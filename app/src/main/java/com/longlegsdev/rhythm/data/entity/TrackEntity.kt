package com.longlegsdev.rhythm.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val description: String,
    val size: Int,
) {
    companion object {
        val EMPTY = TrackEntity(
            id = 0,
            title = "",
            url = "",
            description = "",
            size = 0,
        )
    }
}