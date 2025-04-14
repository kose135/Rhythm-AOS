package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "music")
data class MusicEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Int,
    val lyrics: String,
    val url: String,
    val likes: Int,
    val liked: Boolean
)