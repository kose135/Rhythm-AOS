package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity

@Entity
data class MusicEntity(
    val id: Int,
    val title: String,
    val artist: String?,
    val duration: Int,
    val lyrics: String,
    val url: String,
    val likes: Int,
    val liked: Boolean
)