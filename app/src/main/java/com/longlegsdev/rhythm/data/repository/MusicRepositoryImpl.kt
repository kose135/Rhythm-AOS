package com.longlegsdev.rhythm.data.repository

import com.longlegsdev.rhythm.data.dao.FavoriteMusicDao
import com.longlegsdev.rhythm.data.dao.RecentMusicDao
import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity
import com.longlegsdev.rhythm.data.entity.toFavoriteMusicEntity
import com.longlegsdev.rhythm.data.entity.toRecentMusicEntity
import com.longlegsdev.rhythm.data.mapper.asEntity
import com.longlegsdev.rhythm.data.remote.RhythmApiService
import com.longlegsdev.rhythm.data.remote.model.Apply
import com.longlegsdev.rhythm.data.remote.model.MusicInfo
import com.longlegsdev.rhythm.data.remote.model.MusicList
import com.longlegsdev.rhythm.domain.Result
import com.longlegsdev.rhythm.domain.mapper
import com.longlegsdev.rhythm.domain.repository.MusicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
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

    override suspend fun addMusicLike(
        musicId: Int
    ): Flow<Result<Apply>> {
        return safeApiCall { service.addMusicLike(musicId) }
    }

    override suspend fun deleteMusicLike(
        musicId: Int
    ): Flow<Result<Apply>> {
        return safeApiCall { service.deleteMusicLike(musicId) }
    }

    override suspend fun updateRecentMusic(music: MusicEntity) {
        recentMusicDao.deleteById(music.id)
        recentMusicDao.insert(music.toRecentMusicEntity())
        recentMusicDao.trimToRecentLimit()
    }

    override suspend fun getRecentMusicList(): Flow<Result<List<RecentMusicEntity>>> =
        flow {
            emit(Result.Loading)

            val musics = recentMusicDao.getAllRecentMusicList()
            Timber.d("music list: $musics")

            if (!musics.isEmpty()) {
                emit(Result.Success(musics))
            } else {
                emit(Result.Failure(Throwable("최근 듣을 음악이 없습니다")))
            }

        }.catch { e ->
            e.printStackTrace()
            emit(Result.Failure(e))
        }.flowOn(Dispatchers.IO)

    override suspend fun addFavoriteMusic(music: MusicEntity) {
        try {
            withContext(Dispatchers.IO) {
                favoriteMusicDao.insert(music.toFavoriteMusicEntity())
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

    override suspend fun getAllFavoriteMusicList(): Flow<Result<List<FavoriteMusicEntity>>> =
        flow {
            val musics = favoriteMusicDao.getAllFavoriteMusicList()

            if (!musics.isEmpty()) {
                emit(Result.Success(musics))
            } else {
                emit(Result.Failure(Throwable("즐겨찾기한 음악이 없습니다")))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Result.Failure(e))
        }.flowOn(Dispatchers.IO)

}