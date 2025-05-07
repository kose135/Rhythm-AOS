package com.longlegsdev.rhythm.presentation.screen.main.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.screen.common.card.MusicCard
import com.longlegsdev.rhythm.presentation.screen.common.component.BackTopBar
import com.longlegsdev.rhythm.presentation.screen.common.component.LoadingProgress
import com.longlegsdev.rhythm.presentation.screen.main.section.trackdetail.TrackInfoSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.track.TrackViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMotionApi::class)
@Composable
fun TrackDetailPage(
    onBack: () -> Unit,
    trackViewModel: TrackViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
) {
    val trackEntityInfo by trackViewModel.trackEntityInfo.collectAsState()
    val isFavoriteTrack by trackViewModel.isFavoriteTrack.collectAsState()
    val trackMusicState = trackViewModel.trackMusicState.value

    val scrollState = rememberLazyListState()
    val progress = remember {
        derivedStateOf {
            val offset = minOf(1f, scrollState.firstVisibleItemScrollOffset / 600f)
            offset
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
    ) {

        BackTopBar(
            onBackClick = onBack
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = scrollState,
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {

            item {
                TrackInfoSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    trackEntityInfo = trackEntityInfo,
                    isFavorite = isFavoriteTrack,
                    progress = progress.value,
                    onFavoriteClick = {
                        trackViewModel.toggleFavoriteTrack(trackEntityInfo.id)
                    },
                )
            }

            when {
                trackMusicState.isLoading -> item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingProgress()
                    }
                }

                trackMusicState.errorMessage != null -> item {
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

                trackMusicState.onSuccess == true -> {
                    val musicList = trackMusicState.data!!
                    itemsIndexed(musicList) { index, music ->
                        MusicCard(
                            title = music.title,
                            albumImageUrl = music.album,
                            artist = music.artist,
                            onMusicItemClick = {
                                playerViewModel.play(musicList, index)

                                onBack()
                            }
                        )
                    }
                }
            }
        }
    }
}
