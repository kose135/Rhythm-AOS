package com.longlegsdev.rhythm.data.repository


import com.longlegsdev.rhythm.data.dao.FavoriteChannelDao
import com.longlegsdev.rhythm.data.dao.FavoriteMusicDao
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.data.entity.ChannelListEntity
import com.longlegsdev.rhythm.data.entity.FavoriteChannelEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.entity.toFavoriteChannelEntity
import com.longlegsdev.rhythm.data.entity.toFavoriteMusicEntity
import com.longlegsdev.rhythm.data.mapper.asEntity
import com.longlegsdev.rhythm.data.remote.RhythmApiService
import com.longlegsdev.rhythm.data.remote.model.Apply
import com.longlegsdev.rhythm.data.remote.model.ChannelList
import com.longlegsdev.rhythm.data.remote.model.MusicList
import com.longlegsdev.rhythm.domain.Result
import com.longlegsdev.rhythm.domain.mapper
import com.longlegsdev.rhythm.domain.repository.ChannelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(
    private val service: RhythmApiService,
    private val favoriteChannelDao: FavoriteChannelDao,
) : BaseRepository(), ChannelRepository {

    override suspend fun getChannels(
        page: Int,
        offset: Int
    ): Flow<Result<ChannelListEntity>> {
        return safeApiCall { service.getChannelList(page, offset) }
            .mapper { response: ChannelList ->
                response.asEntity()
            }
    }

    override suspend fun getChannelRecommended(
        limit: Int
    ): Flow<Result<ChannelListEntity>> {
        return safeApiCall { service.getChannelRecommendedList(limit) }
            .mapper { response: ChannelList ->
                response.asEntity()
            }
    }

    override suspend fun getChannelMusic(
        channelId: Int
    ): Flow<Result<MusicListEntity>> {
        return safeApiCall { service.getChannelMusicList(channelId) }
            .mapper { response: MusicList ->
                response.asEntity()
            }
    }

    override suspend fun addChannelLike(
        channelId: Int
    ): Flow<Result<Apply>> {
        return safeApiCall { service.addChannelLike(channelId) }
    }

    override suspend fun deleteChannelLike(
        channelId: Int
    ): Flow<Result<Apply>> {
        return safeApiCall { service.deleteChannelLike(channelId) }
    }

    override suspend fun addFavoriteChannel(channel: ChannelEntity) {
        try {
            withContext(Dispatchers.IO) {
                favoriteChannelDao.insert(channel.toFavoriteChannelEntity())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun deleteFavoriteMusic(channelId: Int) {
        try {
            withContext(Dispatchers.IO) {
                favoriteChannelDao.deleteById(channelId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getAllFavoriteMusicList(): Flow<Result<List<FavoriteChannelEntity>>> =
        flow {
            val channels = favoriteChannelDao.getAllFavoriteChannelList()

            if (!channels.isEmpty()) {
                emit(Result.Success(channels))
            } else {
                emit(Result.Failure(Throwable("즐겨찾기한 채널이 없습니다")))
            }
        }.catch { e ->
            e.printStackTrace()
            emit(Result.Failure(e))
        }.flowOn(Dispatchers.IO)

}