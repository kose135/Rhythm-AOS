package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_music")
data class RecentMusicEntity(
    @PrimaryKey
    val id: Int,
    val playedAt: Long = System.currentTimeMillis() // 마지막 재생시간
)