package com.longlegsdev.rhythm.presentation.screen.common

import android.R.attr.strokeWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingProgress(
    size: Dp = 70.dp,
    color: Color = Color.Black,
    strokeWidth: Dp = 6.dp,
) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(size),
        color = color,
        strokeWidth = strokeWidth,
    )
}