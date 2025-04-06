package com.longlegsdev.rhythm.presentation.screen.main.component

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TabIndicator(
    tabPositions: List<TabPosition>,
    index: Int,
    strokeColor: Color = Color.Black,
) {

    // animation
    require(index in tabPositions.indices) {
        "index ($index) is out of bounds for tabPositions (size=${tabPositions.size})"
    }

    val transition = updateTransition(targetState = index, label = "Tab Indicator Transition")
    val leftIndicator by transition.animateDp(label = "Left") {
        tabPositions[it].left
    }
    val rightIndicator by transition.animateDp(label = "Right") {
        tabPositions[it].right
    }

    // indicator
    Box(
        Modifier
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = leftIndicator)
            .width(rightIndicator - leftIndicator)
            .padding(4.dp)
    )
}