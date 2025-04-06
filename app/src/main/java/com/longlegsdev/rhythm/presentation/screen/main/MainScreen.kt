package com.longlegsdev.rhythm.presentation.screen.main

import android.annotation.SuppressLint
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.longlegsdev.rhythm.presentation.screen.main.component.PageSection
import com.longlegsdev.rhythm.presentation.screen.main.component.TabPage
import com.longlegsdev.rhythm.presentation.screen.main.component.TabSection
import com.longlegsdev.rhythm.presentation.viewmodel.main.MainViewModel
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

    Scaffold(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        Color.White
                    )
                )
            ),
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
        PageSection(pages, pagerState)
    }
}