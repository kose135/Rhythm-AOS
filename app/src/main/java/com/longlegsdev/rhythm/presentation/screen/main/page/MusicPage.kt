package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.util.TableInfo
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.AlbumCard
import com.longlegsdev.rhythm.presentation.screen.main.component.Page
import com.longlegsdev.rhythm.presentation.screen.main.component.PageScreen
import com.longlegsdev.rhythm.presentation.screen.main.section.main.PageSection
import com.longlegsdev.rhythm.presentation.screen.main.section.main.TabSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.AlbumSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.PlayerSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.ProgressSection
import com.longlegsdev.rhythm.presentation.viewmodel.player.PlayerViewModel
import com.longlegsdev.rhythm.util.MUSICENTITY_LIST
import com.longlegsdev.rhythm.util.Space
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MusicPage(
    onSwipeDown: () -> Unit,
) {
    val pages = listOf(PageScreen.Player, PageScreen.Track, PageScreen.Lyrics)
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
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
        Timber.d("Music Page padding values: $it")

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .pointerInput(Unit) {
                    detectVerticalDragGestures { _, dragAmount ->
                        if (dragAmount > 20) { // down swipe
                            onSwipeDown()
                        }
                    }
                },
        ) {
            PageSection(
                type = Page.MUSIC,
                pages = pages,
                pagerState = pagerState,
                modifier = Modifier,
                scrollEnable = true,
            )
        }
    }

}