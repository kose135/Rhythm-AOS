package com.longlegsdev.rhythm.presentation.viewmodel.player.state

import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.service.player.PlaybackState

data class PlayerUiState(
    val playbackState: PlaybackState = PlaybackState.IDLE,
    val music: MusicEntity = MusicEntity.EMPTY,
    val isPlaying: Boolean = false,
    val currentPosition: Long = 0L,
    val bufferedPosition: Long = 0L,
    val duration: Long = 0L
)