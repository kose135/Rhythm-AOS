package com.longlegsdev.rhythm.presentation.screen.common.component

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    height: Dp = 4.dp,
    progress: Long,
    secondaryProgress: Long,
    total: Long,
    changeEnable: Boolean = false,
    progressColor: Color = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
    secondaryProgressColor: Color = Color.LightGray,
    backgroundColor: Color = Color.Black.copy(alpha = 0.5f),
    thumbColor: Color = Color.Gray,
    seekTo: (Long) -> Unit,
) {
    val coercedProgress = progress.coerceAtMost(total)
    val coercedSecondaryProgress = secondaryProgress.coerceAtMost(total)

    val progressRatio = if (total > 0) coercedProgress.toFloat() / total else 0f
    val secondaryRatio = if (total > 0) coercedSecondaryProgress.toFloat() / total else 0f

    var isDragging by remember { mutableStateOf(false) }
    var dragRatio by remember { mutableStateOf(progressRatio) }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(height + 5.dp) // Thumb 공간 확보
            .pointerInput(changeEnable) {
                if (changeEnable) {
                    detectTapGestures { offset ->
                        val clickedRatio = offset.x / size.width
                        seekTo((clickedRatio * total).toLong())
                    }
                }
            }
    ) {
        val boxWidthPx = constraints.maxWidth.toFloat()

        val currentRatio = if (isDragging) dragRatio else progressRatio
        val thumbRadius = 5.dp
        val thumbPx = with(LocalDensity.current) { thumbRadius.toPx() }

        // Background
        Box(
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(50))
                .background(backgroundColor)
        )

        // Secondary progress
        Box(
            Modifier
                .fillMaxWidth(secondaryRatio.coerceIn(0f, 1f))
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(secondaryProgressColor)
        )

        // Primary progress
        Box(
            Modifier
                .fillMaxWidth(currentRatio.coerceIn(0f, 1f))
                .fillMaxHeight()
                .clip(RoundedCornerShape(50))
                .background(progressColor)
        )

        // Thumb
        if (changeEnable) {
            Box(
                Modifier
                    .offset {
                        val x = (currentRatio * boxWidthPx - thumbPx).toInt().coerceIn(0, constraints.maxWidth - thumbPx.toInt())
                        IntOffset(x, 0)
                    }
                    .size(thumbRadius)
                    .clip(CircleShape)
                    .background(thumbColor)
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragStart = {
                                isDragging = true
                            },
                            onDrag = { change, dragAmount ->
                                val newX = (currentRatio * boxWidthPx + dragAmount.x).coerceIn(0f, boxWidthPx)
                                dragRatio = newX / boxWidthPx
                            },
                            onDragEnd = {
                                isDragging = false
                                seekTo((dragRatio * total).toLong())
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