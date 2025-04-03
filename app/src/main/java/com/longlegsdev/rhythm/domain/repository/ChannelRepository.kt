package com.longlegsdev.rhythm.domain.repository

interface ChannelRepository {

    suspend fun getChannels(page: Int = 1, offset: Int = 10)

    suspend fun getChannelRecommended(limit: Int = 20)

    suspend fun getChannelMusic(channelId: Int)

    suspend fun addChannelLike(channelId: Int)

    suspend fun removeChannelLike(channelId: Int)

}