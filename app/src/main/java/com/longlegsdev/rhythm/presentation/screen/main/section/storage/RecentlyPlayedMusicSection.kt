package com.longlegsdev.rhythm.presentation.screen.main.section.storage

import android.R.attr.maxWidth
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.MusicCard
import com.longlegsdev.rhythm.presentation.viewmodel.state.UiState
import com.longlegsdev.rhythm.util.Space

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun RecentlyPlayedMusicSection(
    modifier: Modifier = Modifier,
    state: UiState<List<RecentMusicEntity>>,
    itemWidth: Dp,
    onMusicClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = stringResource(R.string.str_recent_music),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )

        Space(height = 10.dp)

        when {

            state.errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.err_recently_played_music),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            state.onSuccess == true -> {
                val musicList = state.data!!

                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxSize(),
                    rows = GridCells.Fixed(5),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(musicList) { index, music ->
                        val musicId = music.id
                        val title = music.title
                        val albumImageUrl = music.album
                        val artist = music.artist
                        val liked = null

                        MusicCard(
                            musicId = musicId,
                            title = title,
                            albumImageUrl = albumImageUrl,
                            artist = artist,
                            liked = liked,
                            itemWidth = itemWidth,
                            onMusicItemClick = { musicId ->
                                onMusicClick(musicId)
                            }
                        )
                    }
                }
            }
        }
    }
}


