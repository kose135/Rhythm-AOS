package com.longlegsdev.rhythm.presentation.screen.main.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
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
import com.longlegsdev.rhythm.presentation.theme.TabBackground
import com.longlegsdev.rhythm.presentation.theme.TabIconSelected
import com.longlegsdev.rhythm.presentation.theme.TabIconUnselected

@Composable
fun TabSection(
    selectedTabIndex: Int,
    onSelectedTab: (TabPage) -> Unit,
    backgroundColor: Color = TabBackground,
    selectedColor: Color = TabIconSelected,
    unselectedColor: Color = TabIconUnselected,
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
        TabPage.entries.forEachIndexed { index, tabPage ->
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onSelectedTab(tabPage) },
                modifier = Modifier
                    .background(backgroundColor),
                text = {
                    Text(
                        text = stringResource(tabPage.titleResId)
                    )
                },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(tabPage.iconResId),
                        contentDescription = null
                    )
                },
                selectedContentColor = selectedColor,
                unselectedContentColor = unselectedColor
            )
        }
    }
}