package com.longlegsdev.rhythm.presentation.screen.main.section.storage

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.data.entity.TrackEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.TrackCard
import com.longlegsdev.rhythm.presentation.viewmodel.common.state.UiState
import com.longlegsdev.rhythm.util.Space

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun FavoriteChannelSection(
    state: UiState<List<TrackEntity>>,
    onTrackClick: (TrackEntity) -> Unit
) {
    val favoriteTrackList = state.data

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = stringResource(R.string.str_favorite_track),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )

        Space(height = 10.dp)

        if (favoriteTrackList == null || favoriteTrackList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.err_favorite_track),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            LazyRow(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(favoriteTrackList) { index, track ->
                    val id = track.id
                    val title = track.title
                    val coverImageUrl = track.url
                    val description = track.description

                    TrackCard(
                        title = title,
                        coverImageUrl = coverImageUrl,
                        description = description,
                        width = 300.dp,
                        onTrackItemClick = {
                            onTrackClick(track)
                        }
                    )
                }
            }
        }
    }
}


