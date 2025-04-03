package com.longlegsdev.rhythm.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Channel(
    val id: Int,
    val title: String,
    val url: String,
    val size: Int,
    val liked: Boolean
)