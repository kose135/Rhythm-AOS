package com.longlegsdev.rhythm.presentation.screen.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBar(
    height: Dp = 4.dp,
    current: Int,
    max: Int,
    progressColor: Color = Color.White,
    backgroundColor: Color = Color.Gray,
) {
    val safeProgress = if (max > 0) (current.toFloat() / max.toFloat()).coerceIn(0f, 1f) else 0f // 0.0f ~ 1.0f

    LinearProgressIndicator(
        progress = { safeProgress },
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        color = progressColor,
        trackColor = backgroundColor
    )
}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    ProgressBar(
        current = 200,
        max = 300
    )
}

