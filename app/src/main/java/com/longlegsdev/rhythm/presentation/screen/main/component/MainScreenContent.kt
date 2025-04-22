package com.longlegsdev.rhythm.presentation.screen.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.presentation.screen.common.page.PageScreen
import com.longlegsdev.rhythm.presentation.screen.main.section.main.PageSection
import com.longlegsdev.rhythm.presentation.screen.main.section.main.TabSection
import com.longlegsdev.rhythm.presentation.screen.main.state.MainScreenState
import com.longlegsdev.rhythm.presentation.screen.main.state.PlayerState

@Composable
fun MainScreenContent(
    pages: List<PageScreen>,
    pagerState: PagerState,
    playerState: PlayerState,
    uiState: MainScreenState,
    onTabSelected: (Int) -> Unit,
    onPlayPauseClick: () -> Unit,
    onMiniPlayerClick: () -> Unit,
    onMusicPageDismiss: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxSize(),
        bottomBar = {
            TabSection(
                pages = pages,
                selectedTabIndex = pagerState.currentPage,
                onSelectedTab = onTabSelected
            )
        }
    ) { paddingValues ->
        val bottomPadding = paddingValues.calculateBottomPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PageSection(
                pages = pages,
                pagerState = pagerState,
                modifier = Modifier.padding(
                    PaddingValues(
                        bottom = if (uiState.showMiniPlayer) bottomPadding else 0.dp
                    )
                ),
                scrollEnable = false,
            )

            // mini player
            AnimatedMusicMiniPlayer(
                isVisible = uiState.showMiniPlayer,
                bottomPadding = bottomPadding,
                playerState = playerState,
                onPlayPauseClick = onPlayPauseClick,
                onClick = onMiniPlayerClick,
                modifier = Modifier.align(Alignment.BottomCenter)
            )

            // music page
            AnimatedMusicPage(
                isVisible = uiState.showMusicPage,
                onSwipeDown = onMusicPageDismiss,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}