package com.longlegsdev.rhythm.presentation.screen.main.page

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.FavoriteMusicSection
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.FavoriteTrackSection
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.RecentlyPlayedMusicSection
import com.longlegsdev.rhythm.presentation.viewmodel.main.MainViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.storage.StorageViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.track.TrackViewModel
import com.longlegsdev.rhythm.util.Space

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun StoragePage(
    mainViewModel: MainViewModel = hiltViewModel(),
    storageViewModel: StorageViewModel = hiltViewModel(),
    trackViewModel: TrackViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()

    val recentMusicState = storageViewModel.recentMusicListState.value
    val favoriteMusicState = storageViewModel.favoriteMusicListState.value
    val favoriteTrackState = storageViewModel.favoriteTrackEntityListState.value

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        val smallItemWidth = maxWidth / 4 * 3

        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            // 최근 재생 목록
            RecentlyPlayedMusicSection(
                modifier = Modifier,
                state = recentMusicState,
                itemWidth = smallItemWidth,
                onMusicClick = { index ->
                    val list = recentMusicState.data!!

                    playerViewModel.play(list, index)
                }
            )

            Space(height = 15.dp)

            // 즐겨찾기 한 음악
            FavoriteMusicSection(
                state = favoriteMusicState,
                onMusicClick = { index ->
                    val list = favoriteMusicState.data!!

                    playerViewModel.play(list, index)
                }
            )

            Space(height = 15.dp)

            // 즐겨찾기 한 채널
            FavoriteTrackSection(
                state = favoriteTrackState,
                onTrackClick = { track ->
                    trackViewModel.getMusicList(track)
                    mainViewModel.setShowTrackDetailPage(true)
                }
            )

        }
    }
}