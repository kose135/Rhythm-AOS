package com.longlegsdev.rhythm.data.mapper

import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.data.remote.model.Channel

object ChannelMapper: EntityMapper<List<Channel>, List<ChannelEntity>> {
    override fun asEntity(domain: List<Channel>): List<ChannelEntity> {
        return domain.map { channel ->
            ChannelEntity(
                id = channel.id,
                title = channel.title,
                url = channel.url,
                size = channel.size,
                description = channel.description,
                likes = channel.likes,
                liked = channel.liked
            )
        }
    }

    override fun asDomain(entity: List<ChannelEntity>): List<Channel> {
        return entity.map { channelEntity ->
            Channel(
                id = channelEntity.id,
                title = channelEntity.title,
                url = channelEntity.url,
                description = channelEntity.description,
                size = channelEntity.size,
                likes = channelEntity.likes,
                liked = channelEntity.liked
            )
        }
    }
}

fun List<Channel>.asEntity(): List<ChannelEntity> {
    return ChannelMapper.asEntity(this)
}

fun List<ChannelEntity>.asDomain(): List<Channel> {
    return ChannelMapper.asDomain(this.orEmpty())
}