package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.longlegsdev.rhythm.presentation.screen.common.page.PageScreen
import com.longlegsdev.rhythm.presentation.screen.common.page.PageType
import com.longlegsdev.rhythm.presentation.screen.main.section.main.PageSection
import com.longlegsdev.rhythm.presentation.screen.main.section.main.TabSection
import com.longlegsdev.rhythm.presentation.theme.TopTabBackground
import com.longlegsdev.rhythm.presentation.theme.TopTabSelected
import com.longlegsdev.rhythm.presentation.theme.TopTabUnselected
import kotlinx.coroutines.launch

@Composable
fun MusicPage(
    onSwipeDown: () -> Unit,
) {
    val pages = listOf(PageScreen.Player, PageScreen.PlaybackTrack, PageScreen.Lyrics)
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
                type = PageType.MUSIC,
                pages = pages,
                pagerState = pagerState,
                backgroundColor = TopTabBackground,
                selectedColor = TopTabSelected,
                unselectedColor = TopTabUnselected,
                onSelectedTab = { selectedPage ->
                    scope.launch {
                        pagerState.animateScrollToPage(selectedPage)
                    }
                }
            )
        }
    ) {
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
                type = PageType.MUSIC,
                pages = pages,
                pagerState = pagerState,
                modifier = Modifier,
                scrollEnable = true,
            )
        }
    }

}