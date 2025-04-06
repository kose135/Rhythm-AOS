package com.longlegsdev.rhythm.data.repository


import com.longlegsdev.rhythm.data.entity.ChannelListEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.mapper.asEntity
import com.longlegsdev.rhythm.data.remote.RhythmApiService
import com.longlegsdev.rhythm.data.remote.model.Apply
import com.longlegsdev.rhythm.data.remote.model.ChannelList
import com.longlegsdev.rhythm.data.remote.model.MusicList
import com.longlegsdev.rhythm.domain.Result
import com.longlegsdev.rhythm.domain.mapper
import com.longlegsdev.rhythm.domain.repository.ChannelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChannelRepositoryImpl @Inject constructor(
    private val service: RhythmApiService,
//    private val channelDao: ChannelDao,
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

}