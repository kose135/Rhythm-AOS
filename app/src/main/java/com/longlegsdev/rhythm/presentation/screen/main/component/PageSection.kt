package com.longlegsdev.rhythm.presentation.screen.main.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageSection(
    pages: List<TabPage>,
    pagerState: PagerState,
) {
    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 1,
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) { pagePosition ->
        pages[pagePosition].Screen()
    }
}