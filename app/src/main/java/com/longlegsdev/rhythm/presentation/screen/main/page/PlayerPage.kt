package com.longlegsdev.rhythm.presentation.screen.main.page

import android.R.attr.duration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.player.AlbumSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.LikeFavoriteSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.PlayerSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.ProgressSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.util.Space
import kotlinx.coroutines.launch

@Composable
fun PlayerPage(
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    val musicList by playerViewModel.musicList.collectAsState()
    val currentIndex by playerViewModel.currentIndex.collectAsState()
    val currentMusic by playerViewModel.currentMusic.collectAsState()
    val playerState by playerViewModel.playerState.collectAsState()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = currentIndex,
        pageCount = { musicList.size }
    )

    LaunchedEffect(currentIndex) {
        scope.launch {
            pagerState.animateScrollToPage(
                currentIndex,
                animationSpec = tween(500)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AlbumSection(
            modifier = Modifier.weight(2f),
            pagerState = pagerState,
            musicList = musicList
        )

        Space(height = 30.dp)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 30.dp)
                .fillMaxSize()
        ) {
            LikeFavoriteSection(
                isFavorite = false,
                onLikeClick = { },
                onFavoriteClick = { },
            )

            Space(height = 10.dp)

            ProgressSection(
                currentTime = playerState.currentPosition,
                bufferedTime = playerState.bufferedPosition,
                duration = playerState.duration,
                seekTo = { playerViewModel.seekTo(it.toLong()) }
            )

            Space(height = 10.dp)

            PlayerSection(
                modifier = Modifier.weight(1f),
                isPlay = playerState.isPlaying,
                pre = { playerViewModel.previous() },
                playPause = { playerViewModel.playPause() },
                next = { playerViewModel.next() },
            )
        }
    }
}