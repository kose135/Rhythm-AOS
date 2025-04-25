package com.longlegsdev.rhythm.presentation.viewmodel.main.event

import android.app.ProgressDialog.show
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.service.player.PlaybackEvent
import com.longlegsdev.rhythm.service.player.PlaybackState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@Composable
fun HandlePlaybackEvents(
    state: PlaybackState,
    onMiniPlayerShow: (Boolean) -> Unit
) {
    LaunchedEffect(state) {
        when (state) {
            PlaybackState.IDLE -> onMiniPlayerShow(false)
            else -> onMiniPlayerShow(true)
        }
    }
}