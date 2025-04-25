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
import com.longlegsdev.rhythm.presentation.screen.main.section.home.BestMusicListSection
import com.longlegsdev.rhythm.presentation.screen.main.section.home.RecommendedChannelSection
import com.longlegsdev.rhythm.presentation.viewmodel.main.MainViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.util.Space
import timber.log.Timber

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HomePage(
    mainViewModel: MainViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
) {
    val channelListState = mainViewModel.recommendTrackListState.value
    val bestMusicListState = mainViewModel.bestMusicListState.value

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

            RecommendedChannelSection(
                modifier = Modifier.weight(1f),
                state = channelListState,
                onTrackClick = { channel ->
                    Timber.d("click channel id = ${channel.id}")
                    mainViewModel.getMusicList(channel)
                }
            )

            Space(height = 10.dp)

            BestMusicListSection(
                modifier = Modifier.weight(1f),
                state = bestMusicListState,
                itemWidth = itemWidth,
                onMusicClick = { index ->
                    Timber.d("click music index = $index")
                    playerViewModel.play(bestMusicListState.data!!, index)
                }
            )
        }

    }
}