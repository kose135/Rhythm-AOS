package com.longlegsdev.rhythm.presentation.screen.main.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.service.player.PlaybackState

data class PlayerState(
    val playbackState: PlaybackState,
    val music: MusicEntity,
    val isPlaying: Boolean,
    val currentPosition: Long,
    val bufferedPosition: Long,
    val duration: Long
)

@Composable
fun collectPlayerState(
    viewModel: PlayerViewModel
): PlayerState {
    val playbackState by viewModel.playbackState.collectAsState()
    val music by viewModel.currentMusic.collectAsState()
    val isPlay by viewModel.isPlay.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()
    val bufferedPosition by viewModel.bufferedPosition.collectAsState()
    val duration by viewModel.duration.collectAsState()

    return remember(
        playbackState,
        music,
        isPlay,
        currentPosition,
        bufferedPosition,
        duration
    ) {
        PlayerState(
            playbackState = playbackState,
            music = music,
            isPlaying = isPlay,
            currentPosition = currentPosition,
            bufferedPosition = bufferedPosition,
            duration = duration
        )
    }
}

