package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_channel")
data class FavoriteChannelEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val description: String,
    val favoriteAt: Long = System.currentTimeMillis(),
//    val size: Int,
//    val likes: Int = -1,
//    val liked: Boolean
)

fun ChannelEntity.toFavoriteChannelEntity(): FavoriteChannelEntity {
    return FavoriteChannelEntity(
        id = this.id,
        title = this.title,
        url = this.url,
        description = this.description,
    )
}