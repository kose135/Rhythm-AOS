package com.longlegsdev.rhythm.presentation.viewmodel.channel.state

import com.longlegsdev.rhythm.data.entity.ChannelEntity


data class ChannelListState(
    val channels: List<ChannelEntity> = emptyList(),
    val isLoading: Boolean = false,
    val onSuccess: Boolean = false,
    val errorMessage: String? = null
)