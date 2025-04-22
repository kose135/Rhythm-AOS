package com.longlegsdev.rhythm.presentation.screen.main

import android.R.attr.onClick
import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.screen.common.page.PageScreen
import com.longlegsdev.rhythm.presentation.screen.main.component.AnimatedMusicMiniPlayer
import com.longlegsdev.rhythm.presentation.screen.main.component.AnimatedMusicPage
import com.longlegsdev.rhythm.presentation.screen.main.component.MainScreenContent
import com.longlegsdev.rhythm.presentation.screen.main.event.HandlePlaybackEvents
import com.longlegsdev.rhythm.presentation.screen.main.page.MusicPage
import com.longlegsdev.rhythm.presentation.screen.main.section.main.PageSection
import com.longlegsdev.rhythm.presentation.screen.main.section.main.MiniPlayBarSection
import com.longlegsdev.rhythm.presentation.screen.main.section.main.TabSection
import com.longlegsdev.rhythm.presentation.screen.main.state.MainScreenState
import com.longlegsdev.rhythm.presentation.screen.main.state.PlayerState
import com.longlegsdev.rhythm.presentation.screen.main.state.collectPlayerState
import com.longlegsdev.rhythm.presentation.screen.main.state.rememberMainScreenState
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.service.player.PlaybackEvent
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
    val pages = listOf(PageScreen.Channel, PageScreen.Home, PageScreen.Storage)
    val pagerState = rememberPagerState(initialPage = 1) { pages.size }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val playbackState by viewModel.playbackState.collectAsState()


    // UI 상태 관리
    val playerState = collectPlayerState(viewModel)
    val uiState = rememberMainScreenState(playbackState)

    // 이벤트 처리 로직
    HandlePlaybackEvents(viewModel, snackbarHostState)

    // 백 버튼 처리
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




