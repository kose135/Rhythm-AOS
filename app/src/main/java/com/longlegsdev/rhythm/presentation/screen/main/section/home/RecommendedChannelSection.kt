package com.longlegsdev.rhythm.presentation.screen.main.section.home


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.longlegsdev.rhythm.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.ChannelCard
import com.longlegsdev.rhythm.presentation.screen.common.component.LoadingProgress
import com.longlegsdev.rhythm.presentation.viewmodel.state.UiState
import com.longlegsdev.rhythm.util.Space


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun RecommendedChannelSection(
    modifier: Modifier = Modifier,
    state: UiState<List<ChannelEntity>>,
    onTrackClick: (ChannelEntity) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {
        val screenWidth = maxWidth
        val itemWidth = screenWidth * 2 / 3

        Column(
            modifier = modifier
                .background(Color.Transparent)
                .fillMaxSize(),
        ) {

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                text = stringResource(R.string.str_recommended_music),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )

            Space(height = 10.dp)

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

                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
                                title = title,
                                coverImageUrl = coverImageUrl,
                                description = description,
                                size = size,
                                likes = likes,
                                liked = liked,
                                width = itemWidth,
                                onTrackItemClick = {
                                    onTrackClick(channel)
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}

