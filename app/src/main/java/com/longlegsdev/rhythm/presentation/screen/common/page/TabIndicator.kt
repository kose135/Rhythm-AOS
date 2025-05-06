package com.longlegsdev.rhythm.presentation.screen.common.page

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.TabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.floor


@Composable
fun AnimatedTopBarIndicator(
    tabPosition: List<TabPosition>,
    selectedTabIndex: Int,
    indicatorColor: Color = Color.Black,
    indicatorHeight: Dp = 4.dp,
) {
    val transition = updateTransition(targetState = selectedTabIndex, label = "")

    val indicatorStart by transition.animateDp(
        label = "start",
        transitionSpec = { spring(stiffness = Spring.StiffnessMedium) }) { tabPosition[it].left }

    val indicatorEnd by transition.animateDp(
        label = "end",
        transitionSpec = { spring(stiffness = Spring.StiffnessMedium) }) { tabPosition[it].right }

    val indicatorWidth = indicatorEnd - indicatorStart

    Box(
        Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(
                    color = indicatorColor,
                    topLeft = Offset(x = indicatorStart.toPx(), y = 0f),
                    size = Size(width = indicatorWidth.toPx(), height = indicatorHeight.toPx())
                )
            })
}

@Composable
fun AnimatedRibbonIndicator(
    sizeList: SnapshotStateMap<Int, Pair<Float, Float>>, // Tab size info (width, height)
    progressFromFirstPage: Float,                         // Progress from first tab page (e.g., 1.5)
    indicatorColor: Color,                                // Indicator color
    width: Float = 20f,                                   // Indicator stroke width
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind { // Custom drawing behind the Box

                // Stores lengths of each ribbon segment
                val ribbonSectionsLengths = mutableMapOf<Int, Float>()
                var currentRibbonLength = 0f

                // Current start X position
                var currentOrigin = 0f
                val path = Path() // 리본의 전체 경로 / Path for the full ribbon

                // Iterate sorted tabs to build the ribbon path
                sizeList.keys.sorted().mapNotNull { sizeList[it] }
                    .forEachIndexed { index, (width, height) ->
                        // Bottom Y position
                        val bottom = height - 10f
                        // Top Y position
                        val top = 10f

                        // Move to start for first tab
                        if (index == 0) path.moveTo(0f, top)

                        // Curve to top-right
                        path.quadraticTo(
                            currentOrigin + width, top, currentOrigin + width, height / 2
                        )

                        // Curve to bottom-right
                        path.quadraticTo(
                            currentOrigin + width, bottom, currentOrigin + (width / 2), bottom
                        )

                        // Curve to bottom-left
                        path.quadraticTo(
                            currentOrigin + 0f, bottom, currentOrigin + 0f, height / 2
                        )

                        // Curve to top-left
                        path.quadraticTo(
                            currentOrigin - 10f, top, currentOrigin + width, top
                        )

                        // Move to next origin
                        currentOrigin += width

                        // Measure current path length
                        val measure = PathMeasure()
                        measure.setPath(path, false)

                        // Save new ribbon section length
                        val length = measure.length
                        ribbonSectionsLengths[index] = length - currentRibbonLength
                        currentRibbonLength = length
                    }

                // Get decimal progress for animation between tabs
                val progress = progressFromFirstPage - floor(progressFromFirstPage)

                // Determine start and end tab index
                val start =
                    floor(progressFromFirstPage).toInt().coerceIn(0, ribbonSectionsLengths.size - 1)
                val end =
                    ceil(progressFromFirstPage).toInt().coerceIn(0, ribbonSectionsLengths.size - 1)

                // Calculate the active ribbon length
                val ribbonLength =
                    ribbonSectionsLengths[start]!! +
                            ((ribbonSectionsLengths[end]!! - ribbonSectionsLengths[start]!!) * progress)

                // Total length up to start tab
                val lengthUntilStart =
                    ribbonSectionsLengths.keys.sorted().map { ribbonSectionsLengths[it] ?: 0f }
                        .take(start).fold(0f) { acc, it -> acc - it }

                // Total length up to end tab
                val lengthUntilEnd =
                    ribbonSectionsLengths.keys.sorted().map { ribbonSectionsLengths[it] ?: 0f }
                        .take(end).fold(0f) { acc, it -> acc - it }

                // Calculate phase offset for ribbon indicator
                val phaseOffset =
                    lengthUntilStart + ((lengthUntilEnd - lengthUntilStart) * progress)

                // Draw animated dashed indicator
                drawPath(
                    path = path,
                    color = indicatorColor,
                    style = Stroke(
                        width = width, // Stroke thickness
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(
                                ribbonLength, currentRibbonLength // On-off dash interval
                            ),
                            phase = phaseOffset, // Dash starting offset
                        )
                    )
                )
            })
}