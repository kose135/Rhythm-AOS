package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.lyrics.LyricsSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.util.LYRICS

@Composable
fun LyricsPage(
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val lyrics = LYRICS

    LyricsSection(
        lyrics
    )

}