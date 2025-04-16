package com.longlegsdev.rhythm.presentation.screen.main.section.player

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.AlbumCard
import kotlin.math.absoluteValue

@Composable
fun AlbumSection(
    modifier: Modifier,
    pagerState: PagerState,
    musics: List<MusicEntity>,
    contentPadding: Dp = 40.dp,
    pageSpacing: Dp = (-90).dp,
    startAlphaRatio: Float = 0.5f,
    startScaleSizeRatio: Float = 0.5f,
) {

    HorizontalPager(
        modifier = modifier
            .fillMaxWidth(),
        state = pagerState,
        key = { musics[it].id },
        contentPadding = PaddingValues(horizontal = contentPadding),
        pageSpacing = pageSpacing,
        beyondViewportPageCount = 1,
        userScrollEnabled = false
    ) { index ->

        val music = musics[index]
        var url = if (music.album.isNotBlank()) music.album else music.url
        val title = music.title
        val artist = music.artist

        AlbumCard(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset =
                        (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

                    alpha = lerp(
                        start = startAlphaRatio,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                    )

                    val scale = lerp(
                        start = startScaleSizeRatio,
                        stop = 1f,
                        fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
                    )

                    scaleX = scale
                    scaleY = scale

                },
            albumUrl = url,
            title = title,
            artist = artist
        )
    }
}