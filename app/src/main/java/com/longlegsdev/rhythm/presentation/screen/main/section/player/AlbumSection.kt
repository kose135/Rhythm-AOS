package com.longlegsdev.rhythm.presentation.screen.main.section.player

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.media3.exoplayer.ExoPlayer
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.AlbumCard
import com.longlegsdev.rhythm.util.FileType
import kotlin.math.absoluteValue

@Composable
fun AlbumSection(
    pagerState: PagerState,
    musics: List<MusicEntity>,
) {
    val contentPadding = 80.dp
    val pageSpacing = 10.dp
    val scaleSizeRatio = 0.8f

    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth(),
        state = pagerState,
        key = { musics[it].id },
        contentPadding = PaddingValues(horizontal = contentPadding),
        pageSpacing = pageSpacing,
        beyondViewportPageCount = 1,
        userScrollEnabled = true
    ) { index ->
        val music = musics[index]
        var url = if (music.album.isNotBlank()) music.album else music.url

        AlbumCard(
            modifier = Modifier.graphicsLayer {
                val pageOffset =
                    (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

                alpha = lerp(
                    start = 0.5f,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                )

                val scale = lerp(
                    start = scaleSizeRatio,
                    stop = 1f,
                    fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                )

                scaleX = scale
                scaleY = scale

            },
            albumUrl = url,
        )
    }
}