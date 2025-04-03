package com.longlegsdev.rhythm.data.remote.response

import com.longlegsdev.rhythm.data.remote.model.Channel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChannelListResponse(
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "total")
    val total: Int?,
    @field:Json(name = "page")
    val page: Int?,
    @field:Json(name = "size")
    val size: Int,
    @field:Json(name = "list")
    val list: List<Channel>
)