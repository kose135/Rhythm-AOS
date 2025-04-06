package com.longlegsdev.rhythm.domain.repository

import com.longlegsdev.rhythm.data.entity.ChannelListEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.remote.model.Apply
import com.longlegsdev.rhythm.domain.Result
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {

    suspend fun getChannels(page: Int, offset: Int): Flow<Result<ChannelListEntity>>

    suspend fun getChannelRecommended(limit: Int): Flow<Result<ChannelListEntity>>

    suspend fun getChannelMusic(channelId: Int): Flow<Result<MusicListEntity>>

    suspend fun addChannelLike(channelId: Int): Flow<Result<Apply>>

    suspend fun deleteChannelLike(channelId: Int): Flow<Result<Apply>>

}