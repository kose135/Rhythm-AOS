package com.longlegsdev.rhythm.presentation.screen.common.component

import android.R.attr.duration
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.util.Time.currentTime

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    height: Dp = 4.dp,
    current: Long,
    buffer: Long,
    duration: Long,
    changeEnable: Boolean = false,
    progressColor: Color = Color.Red,
    backgroundColor: Color = Color.Yellow.copy(alpha = 0.5f),
    thumbColor: Color = Color.Gray,
    seekTo: (Long) -> Unit,
) {
    val coercedCurrent = current.coerceAtMost(duration)
    val coercedBuffer = buffer.coerceAtMost(duration)
    val progress = if (duration > 0) coercedCurrent.toFloat() / duration else 0f
    val secondaryProgress = if (duration > 0) coercedBuffer.toFloat() / duration else 0f

    var isDragging by remember { mutableStateOf(false) }
    var dragProgress by remember { mutableStateOf(progress) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(height + 16.dp) // Thumb 공간 확보
            .pointerInput(changeEnable) {
                if (changeEnable) {
                    detectTapGestures { offset ->
                        val clickedRatio = offset.x / size.width
                        seekTo((clickedRatio * duration).toLong())
                    }
                }
            }
    ) {
        val boxWidth = constraints.maxWidth.toFloat()

        // background
        Box(
            Modifier
                .fillMaxSize()
                .background(backgroundColor)
        )

        // buffer (secondary progress)
        Box(
            Modifier
                .fillMaxWidth(secondaryProgress.coerceIn(0f, 1f))
                .fillMaxHeight()
                .background(Color.LightGray)
        )

        // progress
        Box(
            Modifier
                .fillMaxWidth((if (isDragging) dragProgress else progress).coerceIn(0f, 1f))
                .fillMaxHeight()
                .background(progressColor)
        )

        // Thumb (draggable circle)
        if (changeEnable) {
            val thumbX = (if (isDragging) dragProgress else progress) * boxWidth

            Box(
                Modifier
                    .offset { IntOffset((thumbX - 12.dp.toPx()).toInt(), 0) }
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(thumbColor)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isDragging = true
                            },
                            onDrag = { change, dragAmount ->
                                val newX = (thumbX + dragAmount.x).coerceIn(0f, boxWidth)
                                dragProgress = newX / boxWidth
                            },
                            onDragEnd = {
                                isDragging = false
                                seekTo((dragProgress * duration).toLong())
                            },
                            onDragCancel = {
                                isDragging = false
                            }
                        )
                    }
            )
        }
    }
}