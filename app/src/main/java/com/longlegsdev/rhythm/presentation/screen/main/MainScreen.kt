package com.longlegsdev.rhythm.presentation.screen.main

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.longlegsdev.rhythm.presentation.screen.main.section.PageSection
import com.longlegsdev.rhythm.presentation.screen.main.section.PlayBarSection
import com.longlegsdev.rhythm.presentation.screen.main.component.TabPage
import com.longlegsdev.rhythm.presentation.screen.main.section.TabSection
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController,
    activity: Activity? = LocalContext.current as? Activity
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

    val pages = TabPage.entries.toList()
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

    val isPlay by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            TabSection(
                selectedTabIndex = pagerState.currentPage,
                onSelectedTab = {
                    scope.launch {
                        pagerState.animateScrollToPage(it.ordinal)
                    }
                }
            )
        }
    ) {
        val bottomHeight = it.calculateBottomPadding()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            PageSection(
                pages = pages,
                pagerState = pagerState,
                modifier = Modifier.weight(1f)
            )

            AnimatedVisibility(
                visible = isPlay,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {

                PlayBarSection(
                    height = bottomHeight,
                    imageUrl = "http://10.0.2.2:8100/cover/IU.jpg",
                    title = "노래 제목",
                    artist = "아티스트",
                    onPlayPauseClick = {

                    }
                )
            }

        }

    }
}

