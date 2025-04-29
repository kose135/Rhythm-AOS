package com.longlegsdev.rhythm.data.repository


import com.longlegsdev.rhythm.data.dao.FavoriteTrackDao
import com.longlegsdev.rhythm.data.entity.TrackEntity
import com.longlegsdev.rhythm.data.entity.TrackListEntity
import com.longlegsdev.rhythm.data.entity.FavoriteTrackEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.mapper.asEntity
import com.longlegsdev.rhythm.data.remote.RhythmApiService
import com.longlegsdev.rhythm.data.remote.model.TrackList
import com.longlegsdev.rhythm.data.remote.model.MusicList
import com.longlegsdev.rhythm.domain.Result
import com.longlegsdev.rhythm.domain.mapper
import com.longlegsdev.rhythm.domain.repository.TrackRepository
import com.longlegsdev.rhythm.domain.result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.collections.joinToString
import kotlin.collections.map

class TrackRepositoryImpl @Inject constructor(
    private val service: RhythmApiService,
    private val favoriteTrackDao: FavoriteTrackDao,
) : BaseRepository(), TrackRepository {

    override suspend fun getTracks(
        page: Int,
        offset: Int
    ): Flow<Result<TrackListEntity>> {
        return safeApiCall { service.getTrackList(page, offset) }
            .mapper { response: TrackList ->
                response.asEntity()
            }
    }

    override suspend fun getRecommendedTracks(
        limit: Int
    ): Flow<Result<TrackListEntity>> {
        return safeApiCall { service.getRecommendedTrackList(limit) }
            .mapper { response: TrackList ->
                response.asEntity()
            }
    }

    override suspend fun getTrackMusic(
        trackId: Int
    ): Flow<Result<MusicListEntity>> {
        return safeApiCall { service.getTrackMusicList(trackId) }
            .mapper { response: MusicList ->
                response.asEntity()
            }
    }

    override suspend fun addFavoriteTrack(track: TrackEntity) {
        try {
            withContext(Dispatchers.IO) {
                favoriteTrackDao.insert(
                    FavoriteTrackEntity(
                        id = track.id
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteFavoriteMusic(trackId: Int) {
        try {
            withContext(Dispatchers.IO) {
                favoriteTrackDao.deleteById(trackId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAllFavoriteTrack(): Flow<Result<List<TrackEntity>>> =
        favoriteTrackDao.getAllFavoriteTrackList()
            .mapLatest { favoriteTrackList ->
                if (favoriteTrackList.isEmpty()) {
                    Result.Failure(Throwable("즐겨찾기한 트랙이 없습니다"))
                } else {
                    val ids = favoriteTrackList.map { it.id }
                    val idsString = ids.joinToString(",")

                    // 서버로부터 받아온 결과를 다시 favoritedAt 기준으로 정렬
                    val response = apiCall { service.getTrackListById(idsString) }
                        .result { result ->
                            // ID -> favoritedAt 매핑
                            val favoriteAtMap =
                                favoriteTrackList.associate { it.id to it.favoritedAt }

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