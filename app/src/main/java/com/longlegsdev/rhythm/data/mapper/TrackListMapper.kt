package com.longlegsdev.rhythm.data.mapper

import com.longlegsdev.rhythm.data.entity.TrackListEntity
import com.longlegsdev.rhythm.data.remote.model.TrackList

object TrackListMapper : EntityMapper<TrackList, TrackListEntity> {
    override fun asEntity(domain: TrackList): TrackListEntity {
        return TrackListEntity(
            total = domain.total,
            page = domain.page,
            size = domain.size,
            channels = domain.list.asEntity()
        )
    }

    override fun asDomain(entity: TrackListEntity): TrackList {
        return TrackList(
            status = "Success Channel Info",
            total = entity.total,
            page = entity.page,
            size = entity.size,
            list = entity.channels.asDomain()
        )
    }
}

fun TrackList.asEntity(): TrackListEntity {
    return TrackListMapper.asEntity(this)
}

fun TrackListEntity.asDomain(): TrackList {
    return TrackListMapper.asDomain(this)
}