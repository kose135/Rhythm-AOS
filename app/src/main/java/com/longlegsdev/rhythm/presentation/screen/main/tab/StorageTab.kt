package com.longlegsdev.rhythm.presentation.screen.main.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.FavoriteChannelSection
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.FavoriteMusicSection
import com.longlegsdev.rhythm.presentation.screen.main.section.storage.RecentlyPlayedMusicSection
import com.longlegsdev.rhythm.presentation.viewmodel.storage.StorageViewModel

@Composable
fun StorageTab(
    viewModel: StorageViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize(),
    ) {
        val scrollState = rememberScrollState()

        val recentMusicState = viewModel.recentMusicListState.value
        val favoriteMusicState = viewModel.favoriteMusicListState.value
        val favoriteChannelState = viewModel.favoriteChannelListState.value

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {

            // 최근 재생 목록
            RecentlyPlayedMusicSection(
                state = recentMusicState,
                onMusicClick = { }
            )

            // 즐겨찾기 한 음악
            FavoriteMusicSection(
                state = favoriteMusicState,
                onMusicClick = { }
            )


            // 즐겨찾기 한 채널
            FavoriteChannelSection(
                state = favoriteChannelState,
                onChannelClick = { }
            )

        }

    }
}