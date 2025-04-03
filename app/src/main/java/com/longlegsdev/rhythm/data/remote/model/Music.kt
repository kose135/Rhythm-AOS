package com.longlegsdev.rhythm.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Music(
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Int,
    val url: String,
    val likes: Int?,
    val liked: Boolean
)