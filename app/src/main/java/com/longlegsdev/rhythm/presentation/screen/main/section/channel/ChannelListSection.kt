package com.longlegsdev.rhythm.presentation.screen.main.section.channel

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.ChannelCard
import com.longlegsdev.rhythm.presentation.screen.common.component.LoadingProgress
import com.longlegsdev.rhythm.presentation.viewmodel.state.UiState

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ChannelListSection(
    modifier: Modifier = Modifier,
    state: UiState<List<ChannelEntity>>,
    onChannelClick: (Int) -> Unit
) {

    BoxWithConstraints(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {
        val screenWidth = maxWidth
        val itemWidth = screenWidth / 2

        Column {
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                text = stringResource(R.string.str_channel_list),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingProgress()
                    }
                }

                state.errorMessage != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.err_network),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                state.onSuccess == true -> {
                    val channels = state.data!!

                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        itemsIndexed(channels) { index, channel ->
                            val channelId = channel.id
                            val title = channel.title
                            val coverImageUrl = channel.url
                            val description = channel.description
                            val size = channel.size
                            val likes = channel.likes
                            val liked = channel.liked

                            ChannelCard(
                                channelId = channelId,
                                title = title,
                                coverImageUrl = coverImageUrl,
                                description = description,
                                size = size,
                                likes = likes,
                                liked = liked,
                                width = itemWidth,
                                onChannelItemClick = { channelId ->
                                    onChannelClick(channelId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
