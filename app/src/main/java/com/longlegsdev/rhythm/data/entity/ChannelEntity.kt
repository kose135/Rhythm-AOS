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
    val liked: Boolean,
    var IsFavorite: Boolean = false
) {
    companion object {
        val EMPTY = ChannelEntity(
            id = -1,
            title = "",
            url = "",
            description = "",
            size = 0,
            likes = 0,
            liked = false,
        )
    }
}