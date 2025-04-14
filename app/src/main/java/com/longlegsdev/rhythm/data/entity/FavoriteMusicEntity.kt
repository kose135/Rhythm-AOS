package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_music")
data class FavoriteMusicEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Int,
    val lyrics: String,
    val url: String,
    val favoriteAt: Long = System.currentTimeMillis()
)

fun MusicEntity.toFavoriteMusicEntity(): FavoriteMusicEntity {
    return FavoriteMusicEntity (
        id = this.id,
        title = this.title,
        artist = this.artist,
        album = this.album,
        duration = this.duration,
        lyrics = this.lyrics,
        url = this.url,
    )
}
