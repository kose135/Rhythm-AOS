package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.channel.TrackListSection
import com.longlegsdev.rhythm.presentation.viewmodel.main.MainViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.track.TrackViewModel
import timber.log.Timber

@Composable
fun TrackPage(
    mainViewModel: MainViewModel = hiltViewModel(),
    trackViewModel: TrackViewModel = hiltViewModel(),
) {
    val trackListState = trackViewModel.trackListState.value

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        TrackListSection(
            state = trackListState,
            onTrackClick = { track ->
                Timber.d("click track id = ${track.id}")
                trackViewModel.getMusicList(track)
                mainViewModel.setShowTrackDetailPage(true)
            }
        )
    }
}