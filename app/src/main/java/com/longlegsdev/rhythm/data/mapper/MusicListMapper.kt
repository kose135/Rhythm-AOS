package com.longlegsdev.rhythm.data.mapper

import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.remote.model.MusicList

object MusicListMapper : EntityMapper<MusicList, MusicListEntity> {
    override fun asEntity(domain: MusicList): MusicListEntity {
        return MusicListEntity(
            size = domain.size ?: domain.list.size,
            musicList = domain.list.asEntity()
        )
    }

    override fun asDomain(entity: MusicListEntity): MusicList {
        return MusicList(
            status = "Success Music List",
            size = entity.size,
            list = entity.musicList.asDomain()
        )
    }
}

fun MusicList.asEntity(): MusicListEntity {
    return MusicListMapper.asEntity(this)
}

fun MusicListEntity.asDomain(): MusicList {
    return MusicListMapper.asDomain(this)
}