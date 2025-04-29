package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_track")
data class FavoriteTrackEntity(
    @PrimaryKey
    val id: Int,
    val favoritedAt: Long = System.currentTimeMillis(),
)