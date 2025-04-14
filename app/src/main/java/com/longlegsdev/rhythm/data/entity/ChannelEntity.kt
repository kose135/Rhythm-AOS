package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ChannelEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val description: String,
    val size: Int,
    val likes: Int = -1,
    val liked: Boolean
)