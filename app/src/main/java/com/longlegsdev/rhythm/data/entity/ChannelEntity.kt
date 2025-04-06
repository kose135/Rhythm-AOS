package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity

@Entity
data class ChannelEntity(
    val id: Int,
    val title: String,
    val url: String,
    val size: Int,
    val likes: Int = -1,
    val liked: Boolean
)