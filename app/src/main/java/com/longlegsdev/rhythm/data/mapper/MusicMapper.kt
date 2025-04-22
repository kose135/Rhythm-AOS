package com.longlegsdev.rhythm.data.mapper

import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.remote.model.Music

object MusicMapper : EntityMapper<Music, MusicEntity> {
    override fun asEntity(domain: Music): MusicEntity {
        return MusicEntity(
            id = domain.id,
            title = domain.title,
            artist = domain.artist,
            album = domain.album ?: "",
            duration = domain.duration * 1000, // 서버는 second 기준
            lyrics = domain.lyrics,
            url = domain.url,
            likes = domain.likes,
            liked = domain.liked
        )
    }

    override fun asDomain(entity: MusicEntity): Music {
        return Music(
            id = entity.id,
            title = entity.title,
            artist = entity.artist,
            album = entity.album,
            duration = entity.duration,
            lyrics = entity.lyrics,
            url = entity.url,
            likes = entity.likes,
            liked = entity.liked
        )
    }
}

fun Music.asEntity(): MusicEntity {
    return MusicMapper.asEntity(this)
}

fun MusicEntity.asDomain(): Music {
    return MusicMapper.asDomain(this)
}

fun List<Music>.asEntity(): List<MusicEntity> {
    return this.map { MusicMapper.asEntity(it) }
}

fun List<MusicEntity>.asDomain(): List<Music> {
    return this.map { MusicMapper.asDomain(it) }
}