package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.tack.TrackSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.util.MUSICENTITY_LIST

@Composable
fun TrackPage(
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val musics = MUSICENTITY_LIST

    var currentIndex by remember { mutableStateOf(0) }
    var currentMusic by remember { mutableStateOf(musics[currentIndex]) }

    TrackSection(
        musicList = musics,
        currentIndex = currentIndex
    )

}