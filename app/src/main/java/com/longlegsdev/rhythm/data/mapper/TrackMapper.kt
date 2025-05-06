package com.longlegsdev.rhythm.data.mapper

import com.longlegsdev.rhythm.data.entity.TrackEntity
import com.longlegsdev.rhythm.data.remote.model.Track
import kotlin.collections.map

object TrackMapper : EntityMapper<Track, TrackEntity> {
    override fun asEntity(domain: Track): TrackEntity {
        return TrackEntity(
            id = domain.id,
            title = domain.title,
            url = domain.url,
            size = domain.size,
            description = domain.description,
        )
    }

    override fun asDomain(entity: TrackEntity): Track {
        return Track(
            id = entity.id,
            title = entity.title,
            url = entity.url,
            description = entity.description,
            size = entity.size,
        )
    }
}

fun Track.asEntity(): TrackEntity {
    return TrackMapper.asEntity(this)
}

fun TrackEntity.asDomain(): Track {
    return TrackMapper.asDomain(this)
}

fun List<Track>.asEntity(): List<TrackEntity> {
    return this.map { TrackMapper.asEntity(it) }
}

fun List<TrackEntity>.asDomain(): List<Track> {
    return this.map { TrackMapper.asDomain(it) }
}