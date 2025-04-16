package com.longlegsdev.rhythm.presentation.screen.common.component

import android.R.attr.duration
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.util.Time.currentTime

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    height: Dp = 4.dp,
    current: Int,
    duration: Int,
    changeEnable: Boolean = false,
    progressColor: Color = Color.Gray,
    backgroundColor: Color = Color.White.copy(alpha = 0.7f),
    seekTo: (Int) -> Unit,
) {

    Slider(
        value = current.coerceAtMost(duration).toFloat(),
        onValueChange = { if (changeEnable) seekTo(it.toInt()) },
        valueRange = 0f..duration.toFloat(),
        steps = 0,
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        colors = SliderDefaults.colors(
            thumbColor = if (changeEnable) progressColor else Color.Transparent,
            activeTrackColor = progressColor,
            inactiveTrackColor = backgroundColor,
        ),
        enabled = changeEnable
    )

}

@Preview(showBackground = true)
@Composable
fun ProgressBarPreview() {
    ProgressBar(
        current = 200,
        duration = 300,
        seekTo = { }
    )
}

