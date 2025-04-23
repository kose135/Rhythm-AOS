package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.lyrics.AlignSection
import com.longlegsdev.rhythm.presentation.screen.main.section.lyrics.LyricsSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.event.AlignmentEvent
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel

@Composable
fun LyricsPage(
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val currentMusic by viewModel.currentMusic.collectAsState()
    var align: AlignmentEvent by remember { mutableStateOf(AlignmentEvent.Left) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AlignSection(
            align = align,
            onAlignClick = { align = it }
        )

        LyricsSection(
            textAlign = align.textAlign,
            lyrics = currentMusic.lyrics
        )

    }
}