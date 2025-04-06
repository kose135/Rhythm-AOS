package com.longlegsdev.rhythm.data.entity


data class ChannelListEntity(
    val total: Int?,
    val page: Int?,
    val size: Int,
    val channels: List<ChannelEntity>
)