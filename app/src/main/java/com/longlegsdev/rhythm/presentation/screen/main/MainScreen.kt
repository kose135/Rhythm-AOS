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
import com.longlegsdev.rhythm.presentation.screen.main.event.HandlePlaybackEvents
import com.longlegsdev.rhythm.presentation.screen.main.state.collectPlayerState
import com.longlegsdev.rhythm.presentation.screen.main.state.rememberMainScreenState
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    activity: Activity? = LocalContext.current as? Activity,
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val pages = listOf(PageScreen.Channel, PageScreen.Home, PageScreen.Storage)
    val pagerState = rememberPagerState(initialPage = 1) { pages.size }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val playbackState by viewModel.playbackState.collectAsState()


    // UI state
    val playerState = collectPlayerState(viewModel)
    val uiState = rememberMainScreenState(playbackState)

    // event processing
    HandlePlaybackEvents(viewModel, snackbarHostState)

    // back button processing
    BackHandler(uiState.showMusicPage) {
        uiState.showMusicPage = false
    }

    BackHandler(enabled = !uiState.showMusicPage) {
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
        },
        onPlayPauseClick = { viewModel.playPause() },
        onMiniPlayerClick = { uiState.showMusicPage = true },
        onMusicPageDismiss = { uiState.showMusicPage = false }
    )
}




