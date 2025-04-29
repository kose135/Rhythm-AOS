package com.longlegsdev.rhythm.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrackList(
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "total")
    val total: Int?,
    @field:Json(name = "page")
    val page: Int?,
    @field:Json(name = "size")
    val size: Int,
    @field:Json(name = "list")
    val list: List<Track>
)