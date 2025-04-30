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
    sizeList: SnapshotStateMap<Int, Pair<Float, Float>>,
    progressFromFirstPage: Float,
    indicatorColor: Color,
    width: Float = 20f,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                val ribbonSectionsLengths = mutableMapOf<Int, Float>()
                var currentRibbonLength = 0f

                var currentOrigin = 0f
                val path = Path()

                sizeList.keys.sorted().mapNotNull { sizeList[it] }
                    .forEachIndexed { index, (width, height) ->
                        val bottom = height - 10f
                        val top = 10f

                        if (index == 0) path.moveTo(0f, top)

                        // top - right
                        path.quadraticTo(
                            currentOrigin + width, top, currentOrigin + width, height / 2
                        )

                        // bottom - right
                        path.quadraticTo(
                            currentOrigin + width, bottom, currentOrigin + (width / 2), bottom
                        )

                        // left?
                        path.quadraticTo(
                            currentOrigin + 0f, bottom, currentOrigin + 0f, height / 2
                        )

                        // top - left
                        path.quadraticTo(
                            currentOrigin - 10f, top, currentOrigin + width, top
                        )

                        currentOrigin += width

                        val measure = PathMeasure()
                        measure.setPath(path, false)

                        val length = measure.length
                        ribbonSectionsLengths[index] = length - currentRibbonLength
                        currentRibbonLength = length
                    }

                val progress = progressFromFirstPage - floor(progressFromFirstPage)
                val start =
                    floor(progressFromFirstPage).toInt().coerceIn(0, ribbonSectionsLengths.size - 1)
                val end =
                    ceil(progressFromFirstPage).toInt().coerceIn(0, ribbonSectionsLengths.size - 1)

                val ribbonLength =
                    ribbonSectionsLengths[start]!! + ((ribbonSectionsLengths[end]!! - ribbonSectionsLengths[start]!!) * progress)

                val lengthUntilStart =
                    ribbonSectionsLengths.keys.sorted().map { ribbonSectionsLengths[it] ?: 0f }
                        .take(start).fold(0f) { acc, it -> acc - it }

                val lengthUntilEnd =
                    ribbonSectionsLengths.keys.sorted().map { ribbonSectionsLengths[it] ?: 0f }
                        .take(end).fold(0f) { acc, it -> acc - it }

                val phaseOffset =
                    lengthUntilStart + ((lengthUntilEnd - lengthUntilStart) * progress)

                drawPath(
                    path = path, color = indicatorColor, style = Stroke(
                        width = width, // 두께
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(
                                ribbonLength, currentRibbonLength
                            ),
                            phase = phaseOffset,
                        )
                    )
                )
            })
}