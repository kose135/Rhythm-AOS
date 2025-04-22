package com.longlegsdev.rhythm.presentation.screen.main.event

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.service.player.PlaybackEvent

@Composable
fun HandlePlaybackEvents(
    viewModel: PlayerViewModel,
    snackbarHostState: SnackbarHostState
) {
    val trackFirstMessage = stringResource(R.string.err_track_first)
    val trackLastMessage = stringResource(R.string.err_track_last)

    LaunchedEffect(Unit) {
        viewModel.playbackEvent.collect { event ->
            when (event) {
                is PlaybackEvent.PlaybackError -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
                PlaybackEvent.StartOfPlaylist -> {
                    snackbarHostState.showSnackbar(
                        message = trackFirstMessage,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
                PlaybackEvent.EndOfPlaylist -> {
                    snackbarHostState.showSnackbar(
                        message = trackLastMessage,
                        withDismissAction = true,
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }
}