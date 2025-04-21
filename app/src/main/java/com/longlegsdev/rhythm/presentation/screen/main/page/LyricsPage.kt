package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.lyrics.LyricsSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel

@Composable
fun LyricsPage(
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val currentMusic by viewModel.currentMusic.collectAsState()

    LyricsSection(
        currentMusic.lyrics
    )

}