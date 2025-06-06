package com.longlegsdev.rhythm.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MusicInfo(
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "info")
    val info: Music
)