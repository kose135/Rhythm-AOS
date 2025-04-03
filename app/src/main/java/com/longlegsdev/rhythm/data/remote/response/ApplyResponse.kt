package com.longlegsdev.rhythm.data.remote.response

import com.longlegsdev.rhythm.data.remote.model.Channel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApplyResponse(
    @field:Json(name = "status")
    val status: String,
    @field:Json(name = "affectedRows")
    val affectedRows: Int,
)