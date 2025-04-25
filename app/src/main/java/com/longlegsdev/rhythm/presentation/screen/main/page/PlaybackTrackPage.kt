package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.playbacktack.TrackSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel

@Composable
fun PlaybackTrackPage(
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val musicList by playerViewModel.musicList.collectAsState()
    val currentIndex by playerViewModel.currentIndex.collectAsState()

    TrackSection(
        musicList = musicList,
        currentIndex = currentIndex,
        onMusicItemClick = { index ->
            playerViewModel.play(index)
        }
    )

}