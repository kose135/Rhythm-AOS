package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_music")
data class RecentMusicEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val lyrics: String,
    val url: String,
    val playedAt: Long = System.currentTimeMillis() // 마지막 재생시간
)

fun MusicEntity.toRecentMusicEntity(): RecentMusicEntity {
    return RecentMusicEntity (
        id = this.id,
        title = this.title,
        artist = this.artist,
        album = this.album,
        duration = this.duration,
        lyrics = this.lyrics,
        url = this.url,
    )
}
