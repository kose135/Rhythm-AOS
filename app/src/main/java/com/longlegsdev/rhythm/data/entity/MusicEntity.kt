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
    val duration: Long,
    val lyrics: String,
    val url: String,
    val likes: Int,
    val liked: Boolean
) {
    companion object {
        val EMPTY = MusicEntity(
            id = -1,
            title = "",
            artist = "",
            album = "",
            duration = 0,
            lyrics = "",
            url = "",
            likes = 0,
            liked = false
        )
    }
}