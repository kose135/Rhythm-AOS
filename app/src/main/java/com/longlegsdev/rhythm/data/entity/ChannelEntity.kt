package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity

@Entity
data class ChannelEntity(
    val id: Int,
    val title: String,
    val url: String,
    val size: Int,
    val liked: Boolean
)