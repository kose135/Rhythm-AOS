package com.longlegsdev.rhythm.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Music(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "artist")
    val artist: String,
    @Json(name = "album")
    val album: String?,
    @Json(name = "duration")
    val duration: Long,
    @Json(name = "lyrics")
    val lyrics: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "likes")
    val likes: Int = -1,
    @Json(name = "liked")
    val liked: Boolean
)