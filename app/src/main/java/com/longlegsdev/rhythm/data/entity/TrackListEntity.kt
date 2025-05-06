package com.longlegsdev.rhythm.data.entity


data class TrackListEntity(
    val total: Int?,
    val page: Int?,
    val size: Int?,
    val tracks: List<TrackEntity>
)