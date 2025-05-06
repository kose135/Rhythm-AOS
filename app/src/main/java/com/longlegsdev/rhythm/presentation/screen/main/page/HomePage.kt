package com.longlegsdev.rhythm.presentation.screen.main.page


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.home.BestMusicSection
import com.longlegsdev.rhythm.presentation.screen.main.section.home.RecommendedTrackSection
import com.longlegsdev.rhythm.presentation.viewmodel.main.MainViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.music.MusicViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.track.TrackViewModel
import com.longlegsdev.rhythm.util.Space

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HomePage(
    mainViewModel: MainViewModel = hiltViewModel(),
    trackViewModel: TrackViewModel = hiltViewModel(),
    musicViewModel: MusicViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
) {
    val recommendTrackState = trackViewModel.recommendTrackState.value
    val bestMusicState = musicViewModel.bestMusicState.value

    BoxWithConstraints(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {
        val itemWidth = maxWidth * 3 / 4

        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize(),
        ) {

            RecommendedTrackSection(
                modifier = Modifier.weight(1f),
                state = recommendTrackState,
                onTrackClick = { trackEntity ->
                    trackViewModel.getMusicList(trackEntity)
                    mainViewModel.setShowTrackDetailPage(true)
                }
            )

            Space(height = 10.dp)

            BestMusicSection(
                modifier = Modifier.weight(1f),
                state = bestMusicState,
                itemWidth = itemWidth,
                onMusicClick = { index ->
                    val list = bestMusicState.data!!

                    playerViewModel.play(list, index)
                }
            )
        }

    }
}