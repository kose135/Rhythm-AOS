package com.longlegsdev.rhythm.presentation.screen.main.section

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.longlegsdev.rhythm.presentation.screen.main.component.PageScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageSection(
    pages: List<PageScreen>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    scrollEnable: Boolean = false
) {
    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 1,
        userScrollEnabled = scrollEnable,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        Color.White,
                        Color.White,
                    )
                )
            )
            .fillMaxSize()
    ) { pagePosition ->
        pages[pagePosition].Content()
    }
}