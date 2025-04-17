package com.longlegsdev.rhythm.presentation.screen.main

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.longlegsdev.rhythm.presentation.screen.main.component.PageScreen
import com.longlegsdev.rhythm.presentation.screen.main.page.MusicPage
import com.longlegsdev.rhythm.presentation.screen.main.section.main.PageSection
import com.longlegsdev.rhythm.presentation.screen.main.section.main.TabSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    val pages = listOf(PageScreen.Channel, PageScreen.Home, PageScreen.Storage)
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { pages.size }
    )
    val scope = rememberCoroutineScope()

    var expandedPlayer by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            PageSection(
                pages = pages,
                pagerState = pagerState,
                modifier = Modifier,
                scrollEnable = false,
            )

            AnimatedVisibility(
                visible = expandedPlayer,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 1000)
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 500)
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                MusicPage(
                    sharedAlbumImage = { },

                )
            }
        }

    }
}

