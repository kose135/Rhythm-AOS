package com.longlegsdev.rhythm.presentation.screen.main

import android.R.attr.bottom
import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.longlegsdev.rhythm.presentation.screen.main.component.PageScreen
import com.longlegsdev.rhythm.presentation.screen.main.page.MusicPage
import com.longlegsdev.rhythm.presentation.screen.main.section.main.PageSection
import com.longlegsdev.rhythm.presentation.screen.main.section.main.MiniPlayBarSection
import com.longlegsdev.rhythm.presentation.screen.main.section.main.TabSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.service.player.PlaybackState
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    activity: Activity? = LocalContext.current as? Activity,
    viewModel: PlayerViewModel = hiltViewModel(),
) {

    /* back key event */
//    BackHandler {
//        if (deleteEnable) {
//            deleteEnable = false
//            selectedNotes.clear()
//        } else {
//            activity?.finish()
//        }
//    }

    val pages = listOf(PageScreen.Channel, PageScreen.Home, PageScreen.Storage)
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

    val music by viewModel.currentMusic.collectAsState()
    val playerState by viewModel.playbackState.collectAsState()
    val isPlay by viewModel.isPlay.collectAsState()
    val currentPosition by viewModel.currentPosition.collectAsState()
    val bufferedPosition by viewModel.bufferedPosition.collectAsState()
    val duration by viewModel.duration.collectAsState()

    var showMiniPlayer by remember { mutableStateOf(false) }
    var showMusicPage by remember { mutableStateOf(false) }

    // playerState 변경을 감지하여 showMiniPlayer 업데이트
    LaunchedEffect(playerState) {
        Timber.d("player state is ${playerState}")
        showMiniPlayer = (playerState != PlaybackState.IDLE)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            TabSection(
                pages = pages,
                selectedTabIndex = pagerState.currentPage,
                onSelectedTab = { selectedPage ->
                    scope.launch {
                        pagerState.animateScrollToPage(selectedPage)
                    }
                }
            )
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PageSection(
                pages = pages,
                pagerState = pagerState,
                modifier = Modifier
                    .padding(
                        PaddingValues(
                            bottom = if (showMiniPlayer) bottomPadding else 0.dp
                        )
                    ),
                scrollEnable = false,
            )

            // mini player bar
            AnimatedVisibility(
                visible = showMiniPlayer,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 200)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 200)
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                MiniPlayBarSection(
                    height = bottomPadding,
                    isPlay = isPlay,
                    imageUrl = music.url,
                    title = music.title,
                    artist = music.artist,
                    currentPosition = currentPosition,
                    bufferedPosition = bufferedPosition,
                    duration = duration,
                    onPlayPauseClick = { viewModel.playPause() },
                    onMiniPlayerBarClick = { showMusicPage = true }
                )
            }

            // music page
            AnimatedVisibility(
                visible = showMusicPage,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 200)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 200)
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                MusicPage(
                    onSwipeDown = { showMusicPage = false }
                )
            }
        }

    }
}

