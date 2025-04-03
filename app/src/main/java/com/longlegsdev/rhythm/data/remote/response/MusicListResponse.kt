package com.longlegsdev.rhythm.data.remote.response

import com.longlegsdev.rhythm.data.remote.model.Channel
import com.longlegsdev.rhythm.data.remote.model.Music
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MusicListResponse(
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "size")
    val size: Int?,
    @field:Json(name = "list")
    val list: List<Music>
)