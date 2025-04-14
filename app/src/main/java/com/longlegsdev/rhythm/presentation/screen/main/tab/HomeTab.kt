package com.longlegsdev.rhythm.presentation.screen.main.tab


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.home.HomeMusicSection
import com.longlegsdev.rhythm.presentation.screen.main.section.home.RecommendedChannelSection
import com.longlegsdev.rhythm.presentation.viewmodel.main.HomeViewModel
import com.longlegsdev.rhythm.util.Space
import timber.log.Timber

@Composable
fun HomeTab(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val channelListState = viewModel.channelListState.value
    val musicListState = viewModel.musicListState.value

    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
    ) {

        RecommendedChannelSection(
            modifier = Modifier.weight(1f),
            state = channelListState,
            onChannelClick = { channelId ->
                Timber.d("click channel id = $channelId")
            }
        )

        Space(height = 10.dp)

        HomeMusicSection(
            modifier = Modifier.weight(1f),
            state = musicListState,
            onMusicClick = { musicId ->
                Timber.d("click music id = $musicId")

            }
        )

    }
}