package com.longlegsdev.rhythm.presentation.screen.main.section.main

import androidx.compose.foundation.background
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.longlegsdev.rhythm.presentation.screen.common.page.AnimatedRibbonIndicator
import com.longlegsdev.rhythm.presentation.screen.common.page.AnimatedTopBarIndicator
import com.longlegsdev.rhythm.presentation.screen.common.page.PageScreen
import com.longlegsdev.rhythm.presentation.screen.common.page.PageType
import com.longlegsdev.rhythm.presentation.theme.BottomTabBackground
import com.longlegsdev.rhythm.presentation.theme.BottomTabSelected
import com.longlegsdev.rhythm.presentation.theme.BottomTabUnselected
import com.longlegsdev.rhythm.util.offsetForPage

@Composable
fun TabSection(
    type: PageType = PageType.MAIN,
    pages: List<PageScreen>,
    pagerState: PagerState,
    onSelectedTab: (Int) -> Unit,
    backgroundColor: Color = BottomTabBackground,
    selectedColor: Color = BottomTabSelected,
    unselectedColor: Color = BottomTabUnselected,
) {
    when (type) {
        PageType.MAIN -> {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = {
                    AnimatedTopBarIndicator(
                        tabPosition = it,
                        selectedTabIndex = pagerState.currentPage,
                        indicatorColor = selectedColor,
                    )
                }
            ) {
                pages.forEachIndexed { index, tabPage ->
                    // Icon, Text 표기
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = { onSelectedTab(index) },
                        modifier = Modifier
                            .background(backgroundColor),
                        text = {
                            Text(text = stringResource(tabPage.title))
                        },
                        icon = {
                            Icon(
                                imageVector = ImageVector.vectorResource(tabPage.icon!!),
                                contentDescription = null
                            )
                        },
                        selectedContentColor = selectedColor,
                        unselectedContentColor = unselectedColor
                    )
                }

            }
        }


        PageType.MUSIC -> {
            val sizeList = remember { mutableStateMapOf<Int, Pair<Float, Float>>() }
            val progressFromFirstPage by remember {
                derivedStateOf {
                    pagerState.offsetForPage(0)
                }
            }

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = {
                    AnimatedRibbonIndicator(
                        sizeList = sizeList,
                        progressFromFirstPage = progressFromFirstPage,
                        indicatorColor = selectedColor,
                    )
                }
            ) {
                pages.forEachIndexed { index, tabPage ->
                    // Text만 표기
                    Tab(
                        selected = index == pagerState.currentPage,
                        onClick = { onSelectedTab(index) },
                        modifier = Modifier
                            .background(backgroundColor)
                            .onSizeChanged {
                                sizeList[index] =
                                    Pair(it.width.toFloat(), it.height.toFloat())
                            },
                        text = {
                            Text(text = stringResource(tabPage.title))
                        },
                        selectedContentColor = selectedColor,
                        unselectedContentColor = unselectedColor
                    )

                }

            }
        }
    }
}