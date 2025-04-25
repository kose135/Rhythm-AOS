package com.longlegsdev.rhythm.presentation.screen.main.page

import android.R.attr.track
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.FavoriteChannelSection
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.FavoriteMusicSection
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.RecentlyPlayedMusicSection
import com.longlegsdev.rhythm.presentation.viewmodel.main.MainViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.storage.StorageViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun StoragePage(
    storageViewModel: StorageViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
) {
    val scrollState = rememberScrollState()

    val recentMusicState = storageViewModel.recentMusicListState.value
    val favoriteMusicState = storageViewModel.favoriteMusicListState.value
    val favoriteChannelState = storageViewModel.favoriteChannelListState.value

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        val sectionHeight = maxHeight / 3 * 2
        val smallItemWidth = maxWidth / 4 * 3
        val bigItemWidth = maxWidth / 5 * 2

        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            // 최근 재생 목록
            RecentlyPlayedMusicSection(
                modifier = Modifier
                    .height(sectionHeight),
                state = recentMusicState,
                itemWidth = smallItemWidth,
                onMusicClick = { }
            )

            // 즐겨찾기 한 음악
            FavoriteMusicSection(
                modifier = Modifier
                    .height(sectionHeight),
                state = favoriteMusicState,
                onMusicClick = { }
            )

            // 즐겨찾기 한 채널
            FavoriteChannelSection(
                modifier = Modifier
                    .height(sectionHeight),
                state = favoriteChannelState,
                onTrackClick = {
//                    track -> mainViewModel.getMusicList(track)
                }
            )

        }
    }
}