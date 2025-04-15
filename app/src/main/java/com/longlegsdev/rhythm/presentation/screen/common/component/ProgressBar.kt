package com.longlegsdev.rhythm.presentation.screen.common.component

import android.R.attr.duration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.util.Time.currentTime

@Composable
fun ProgressBar(
    height: Dp = 4.dp,
    current: Int,
    duration: Int,
    progressColor: Color = Color.White,
    backgroundColor: Color = Color.Gray,
    changeEnable: Boolean = false,
    seekTo: (Int) -> Unit,
) {
    Slider(
        modifier = Modifier
            .height(height)
            .fillMaxWidth(),
        value = current.toFloat(),
        onValueChange = { if (changeEnable) seekTo(it.toInt()) },
        valueRange = 0f..duration.toFloat(),
        steps = 1,
        colors = SliderDefaults.colors(
            thumbColor = Color.Green,
            activeTrackColor = progressColor,
            inactiveTrackColor = backgroundColor,
        )
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

