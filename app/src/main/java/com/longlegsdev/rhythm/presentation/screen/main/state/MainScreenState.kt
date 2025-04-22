package com.longlegsdev.rhythm.presentation.screen.main.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.service.player.PlaybackState

class MainScreenState(
    showMiniPlayer: Boolean,
    showMusicPage: Boolean
) {
    var showMiniPlayer by mutableStateOf(showMiniPlayer)
    var showMusicPage by mutableStateOf(showMusicPage)
}

@Composable
fun rememberMainScreenState(
    playerState: PlaybackState
): MainScreenState {
    var showMiniPlayer by remember { mutableStateOf(false) }
    var showMusicPage by remember { mutableStateOf(false) }

    // playerState 변경을 감지하여 showMiniPlayer 업데이트
    LaunchedEffect(playerState) {
        showMiniPlayer = (playerState != PlaybackState.IDLE)
    }

    return remember(showMiniPlayer, showMusicPage) {
        MainScreenState(
            showMiniPlayer = showMiniPlayer,
            showMusicPage = showMusicPage
        )
    }
}


