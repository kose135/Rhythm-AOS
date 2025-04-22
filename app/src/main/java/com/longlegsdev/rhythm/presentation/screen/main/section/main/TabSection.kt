package com.longlegsdev.rhythm.presentation.screen.main.section.main

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.longlegsdev.rhythm.presentation.screen.common.page.PageScreen
import com.longlegsdev.rhythm.presentation.screen.common.page.TabIndicator
import com.longlegsdev.rhythm.presentation.theme.BottomTabBackground
import com.longlegsdev.rhythm.presentation.theme.BottomTabSelected
import com.longlegsdev.rhythm.presentation.theme.BottomTabUnselected

@Composable
fun TabSection(
    pages: List<PageScreen>,
    selectedTabIndex: Int,
    onSelectedTab: (Int) -> Unit,
    backgroundColor: Color = BottomTabBackground,
    selectedColor: Color = BottomTabSelected,
    unselectedColor: Color = BottomTabUnselected,
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = {
            TabIndicator(
                tabPositions = it,
                index = selectedTabIndex
            )
        }
    ) {
        pages.forEachIndexed { index, tabPage ->
            if (tabPage.icon != null) {
                // Icon, Text 표기
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { onSelectedTab(index) },
                    modifier = Modifier.background(backgroundColor),
                    text = {
                        Text(text = stringResource(tabPage.title))
                    },
                    icon = {
                        Icon(
                            imageVector = ImageVector.vectorResource(tabPage.icon),
                            contentDescription = null
                        )
                    },
                    selectedContentColor = selectedColor,
                    unselectedContentColor = unselectedColor
                )
            } else {
                // Text만 표기
                Tab(
                    selected = index == selectedTabIndex,
                    onClick = { onSelectedTab(index) },
                    modifier = Modifier.background(backgroundColor),
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