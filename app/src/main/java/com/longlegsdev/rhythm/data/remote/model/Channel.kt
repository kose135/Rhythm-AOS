package com.longlegsdev.rhythm.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Channel(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "cover_url")
    val url: String,
    @Json(name = "size")
    val size: Int,
    @Json(name = "likes")
    val likes: Int = -1,
    @Json(name = "liked")
    val liked: Boolean
)