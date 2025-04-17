package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.longlegsdev.rhythm.presentation.screen.main.section.player.AlbumSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.LikeFavoriteSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.PlayerSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.ProgressSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.util.MUSICENTITY_LIST
import com.longlegsdev.rhythm.util.Space
import com.longlegsdev.rhythm.util.Time.currentTime
import kotlinx.coroutines.launch

@Composable
fun PlayerPage(
    viewModel: PlayerViewModel = hiltViewModel()
) {
    val musics = MUSICENTITY_LIST

    var currentIndex by remember { mutableStateOf(0) }
    var currentMusic by remember { mutableStateOf(musics[currentIndex]) }
    var currentTime = remember { mutableIntStateOf(32) }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = currentIndex,
        pageCount = { musics.size }
    )

    LaunchedEffect(currentIndex) {
        currentMusic = musics[currentIndex % musics.size]
        scope.launch {
            pagerState.animateScrollToPage(currentIndex)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AlbumSection(
            modifier = Modifier
                .weight(2f),
            pagerState = pagerState,
            musics = musics
        )

        Space(height = 30.dp)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 30.dp)
                .fillMaxSize()
                .background(Color.Transparent)
        ) {

            LikeFavoriteSection(
                isLike = currentMusic.liked,
                likeCount = currentMusic.likes,
                isFavorite = false,
                onLikeClick = { },
                onFavoriteClick = { },
            )

            Space(height = 10.dp)

            ProgressSection(
                currentTime = currentTime.value,
                duration = currentMusic.duration,
                seekTo = { currentTime.value = it }
            )

            Space(height = 10.dp)

            PlayerSection(
                modifier = Modifier
                    .weight(1f),
                pre = { currentIndex-- },
                playPause = { },
                next = { currentIndex++ },
            )
        }
    }
}