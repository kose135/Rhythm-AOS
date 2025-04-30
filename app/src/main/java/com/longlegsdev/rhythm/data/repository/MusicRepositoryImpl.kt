package com.longlegsdev.rhythm.data.repository

import androidx.compose.ui.res.stringResource
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.data.dao.FavoriteMusicDao
import com.longlegsdev.rhythm.data.dao.RecentMusicDao
import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity
import com.longlegsdev.rhythm.data.mapper.asEntity
import com.longlegsdev.rhythm.data.remote.RhythmApiService
import com.longlegsdev.rhythm.data.remote.model.MusicInfo
import com.longlegsdev.rhythm.data.remote.model.MusicList
import com.longlegsdev.rhythm.domain.Result
import com.longlegsdev.rhythm.domain.mapper
import com.longlegsdev.rhythm.domain.repository.MusicRepository
import com.longlegsdev.rhythm.domain.result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val service: RhythmApiService,
    private val recentMusicDao: RecentMusicDao,
    private val favoriteMusicDao: FavoriteMusicDao,
) : BaseRepository(), MusicRepository {

    override suspend fun getMusicInfo(
        musicId: Int
    ): Flow<Result<MusicEntity>> {
        return safeApiCall { service.getMusicInfo(musicId) }
            .mapper { response: MusicInfo ->
                response.info.asEntity()
            }
    }

    override suspend fun getBestMusic(
        limit: Int
    ): Flow<Result<MusicListEntity>> {
        return safeApiCall { service.getMusicBest(limit) }
            .mapper { response: MusicList ->
                response.asEntity()
            }
    }

    override suspend fun updateRecentMusic(music: MusicEntity) {
        recentMusicDao.deleteById(music.id)
        recentMusicDao.insert(RecentMusicEntity(id = music.id))
        recentMusicDao.trimToRecentLimit()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getRecentMusicList(): Flow<Result<List<MusicEntity>>> =
        recentMusicDao.getAllRecentMusicList()
            .mapLatest { recentList ->
                if (recentList.isEmpty()) {
                    Result.Failure(Throwable("최근 재생 된 음악이 없습니다"))
                } else {
                    val ids = recentList.map { it.id }
                    val idsString = ids.joinToString(",")

                    // 서버로부터 받아온 결과를 다시 playedAt 기준으로 정렬
                    val response = apiCall { service.getMusicListById(idsString) }
                        .result { result ->
                            // ID -> playedAt 매핑
                            val playedAtMap = recentList.associate { it.id to it.playedAt }

                            // 서버에서 받은 리스트를 playedAt 기준으로 정렬
                            result.list
                                .map { it.asEntity() }
                                .sortedByDescending { playedAtMap[it.id] ?: 0L }
                        }

                    response
                }
            }
            .onStart { emit(Result.Loading) }
            .catch { e ->
                e.printStackTrace()
                emit(Result.Failure(Exception(e)))
            }
            .flowOn(Dispatchers.IO)


    override suspend fun addFavoriteMusic(music: MusicEntity) {
        try {
            withContext(Dispatchers.IO) {
                favoriteMusicDao.insert(FavoriteMusicEntity(id = music.id))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteFavoriteMusic(musicId: Int) {
        try {
            withContext(Dispatchers.IO) {
                favoriteMusicDao.deleteById(musicId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllFavoriteMusicList(): Flow<Result<List<MusicEntity>>> =
        favoriteMusicDao.getAllFavoriteMusicList()
            .mapLatest { favoriteMusicList ->
                if (favoriteMusicList.isEmpty()) {
                    Result.Success(emptyList())
                } else {
                    val ids = favoriteMusicList.map { it.id }
                    val idsString = ids.joinToString(",")

                    // 서버로부터 받아온 결과를 다시 favoritedAt 기준으로 정렬
                    val response = apiCall { service.getMusicListById(idsString) }
                        .result { result ->
                            // ID -> favoritedAt 매핑
                            val favoriteAtMap = favoriteMusicList.associate { it.id to it.favoritedAt }

                            // 서버에서 받은 리스트를 favoritedAt 기준으로 정렬
                            result.list
                                .map { it.asEntity() }
                                .sortedByDescending { favoriteAtMap[it.id] ?: 0L }
                        }

                    response
                }
            }
            .onStart { emit(Result.Loading) }
            .catch { e ->
                e.printStackTrace()
                emit(Result.Failure(Exception(e)))
            }
            .flowOn(Dispatchers.IO)

}