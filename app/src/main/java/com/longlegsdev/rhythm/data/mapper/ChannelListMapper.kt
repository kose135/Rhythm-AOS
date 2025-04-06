package com.longlegsdev.rhythm.data.mapper

import com.longlegsdev.rhythm.data.entity.ChannelListEntity
import com.longlegsdev.rhythm.data.remote.model.ChannelList

object ChannelListMapper : EntityMapper<ChannelList, ChannelListEntity> {
    override fun asEntity(domain: ChannelList): ChannelListEntity {
        return ChannelListEntity(
            total = domain.total,
            page = domain.page,
            size = domain.size,
            channels = domain.list.asEntity()
        )
    }

    override fun asDomain(entity: ChannelListEntity): ChannelList {
        return ChannelList(
            status = "Success Channel Info",
            total = entity.total,
            page = entity.page,
            size = entity.size,
            list = entity.channels.asDomain()
        )
    }
}

fun ChannelList.asEntity(): ChannelListEntity {
    return ChannelListMapper.asEntity(this)
}

fun ChannelListEntity.asDomain(): ChannelList {
    return ChannelListMapper.asDomain(this)
}