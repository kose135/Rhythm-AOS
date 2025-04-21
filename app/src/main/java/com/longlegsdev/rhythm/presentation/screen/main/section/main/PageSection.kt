package com.longlegsdev.rhythm.presentation.screen.main.section.main

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
import com.longlegsdev.rhythm.presentation.screen.main.component.Page
import com.longlegsdev.rhythm.presentation.screen.main.component.Page.*
import com.longlegsdev.rhythm.presentation.screen.main.component.PageScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PageSection(
    type: Page = Page.MAIN,
    pages: List<PageScreen>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    scrollEnable: Boolean = false
) {
    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 2,
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

        when(type) {
            MAIN -> pages[pagePosition].MainContent()
            MUSIC -> pages[pagePosition].MusicContent()
        }

    }
}