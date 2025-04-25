package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.channel.ChannelListSection
import com.longlegsdev.rhythm.presentation.viewmodel.main.MainViewModel
import timber.log.Timber

@Composable
fun ChannelPage(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val channelListState = viewModel.trackListState.value

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        ChannelListSection(
            state = channelListState,
            onTrackClick = { channel ->
                Timber.d("click channel id = ${channel.id}")
                viewModel.getMusicList(channel)
            }
        )

    }
}