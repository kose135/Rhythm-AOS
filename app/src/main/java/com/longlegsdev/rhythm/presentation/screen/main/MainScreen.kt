package com.longlegsdev.rhythm.presentation.screen.main

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.longlegsdev.rhythm.presentation.screen.common.page.PageScreen
import com.longlegsdev.rhythm.presentation.screen.main.component.MainScreenContent
import com.longlegsdev.rhythm.presentation.viewmodel.main.event.HandlePlayEvents
import com.longlegsdev.rhythm.presentation.viewmodel.main.MainViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.main.event.HandlePlaybackEvents
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "StateFlowValueCalledInComposition")
@Composable
fun MainScreen(
    navController: NavHostController,
    activity: Activity? = LocalContext.current as? Activity,
    mainViewModel: MainViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel(),
) {
    val pages = listOf(PageScreen.Channel, PageScreen.Home, PageScreen.Storage)
    val pagerState = rememberPagerState(initialPage = 1) { pages.size }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // UI state
    val playerState by playerViewModel.playerState.collectAsState()
    val playbackState by playerViewModel.playbackState.collectAsState()
    val playerEvent = playerViewModel.playbackEvents
    val uiState by mainViewModel.uiState.collectAsState()

    // event processing
    HandlePlaybackEvents(playbackState) { isShow -> mainViewModel.setShowMiniPlayer(isShow) }
    HandlePlayEvents(playerEvent, snackbarHostState)

    // back button processing
    BackHandler {
        if (uiState.showTrackDetailPage) {
            mainViewModel.setShowTrackDetailPage(false)
        } else if (uiState.showMusicPage) {
            mainViewModel.setShowMusicPage(false)
        }
    }

    BackHandler(enabled = !uiState.showMusicPage && !uiState.showTrackDetailPage) {
        activity?.finish()
    }

    MainScreenContent(
        pages = pages,
        pagerState = pagerState,
        playerState = playerState,
        uiState = uiState,
        onTabSelected = { selectedPage ->
            scope.launch {
                pagerState.animateScrollToPage(selectedPage)
            }

            mainViewModel.setShowMusicPage(false)
            mainViewModel.setShowTrackDetailPage(false)
        },
        onPlayPauseClick = { playerViewModel.playPause() },
        onMusicPageShow = { isShow -> mainViewModel.setShowMusicPage(isShow) },
        onTrackDetailPageShow = { isShow -> mainViewModel.setShowTrackDetailPage(isShow) }
    )
}




