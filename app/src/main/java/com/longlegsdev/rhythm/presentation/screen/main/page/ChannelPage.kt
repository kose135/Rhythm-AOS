package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.channel.ChannelListSection
import com.longlegsdev.rhythm.presentation.viewmodel.channel.ChannelViewModel
import timber.log.Timber

@Composable
fun ChannelPage(
    viewModel: ChannelViewModel = hiltViewModel(),
) {

    val channelListState = viewModel.channelListState.value

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        ChannelListSection(
            state = channelListState,
            onChannelClick = { channelId ->
                Timber.d("click channel id = $channelId")
                viewModel.playChannel(channelId)
            }
        )

    }
}