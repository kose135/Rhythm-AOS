package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.Int

@Entity(tableName = "favorite_music")
data class FavoriteMusicEntity(
    @PrimaryKey
    val id: Int,
    val favoritedAt: Long = System.currentTimeMillis()
)