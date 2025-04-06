package com.longlegsdev.rhythm.presentation.screen.main.tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.channel.ChannelViewModel

@Composable
fun ChannelTab (
//    viewModel: ChannelViewModel = hiltViewModel(),
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Channel Tab")

    }
}