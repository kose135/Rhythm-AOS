package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity

data class MusicListEntity(
    val size: Int,
    val musics: List<MusicEntity>
)