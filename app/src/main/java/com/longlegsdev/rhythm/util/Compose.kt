package com.longlegsdev.rhythm.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Space(height: Dp = 0.dp, width: Dp = 0.dp) = Spacer(
    Modifier
        .height(height)
        .width(width)
)

inline fun Modifier.click(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.offsetForPage(page: Int) = (currentPage - page) + currentPageOffsetFraction