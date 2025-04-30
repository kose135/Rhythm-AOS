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
import com.longlegsdev.rhythm.util.offsetForPage
import timber.log.Timber
import kotlin.math.absoluteValue

@Composable
fun AlbumSection(
    modifier: Modifier,
    pagerState: PagerState,
    musicList: List<MusicEntity>,
    contentPadding: Dp = 40.dp,
    pageSpacing: Dp = (-90).dp,
    startAlphaRatio: Float = 0.5f,
    startScaleSizeRatio: Float = 0.5f,
) {

    HorizontalPager(
        modifier = modifier
            .fillMaxWidth(),
        state = pagerState,
        key = { musicList[it].id },
        contentPadding = PaddingValues(horizontal = contentPadding),
        pageSpacing = pageSpacing,
        beyondViewportPageCount = 1,
        userScrollEnabled = false,
    ) { pageNum ->
        val pageOffset = pagerState.offsetForPage(pageNum)

        // animation value
        // alpha
        val alpha = lerp(
            start = startAlphaRatio,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue
        )
        // scale
        val scale = lerp(
            start = startScaleSizeRatio,
            stop = 1f,
            fraction = 1f - pageOffset.absoluteValue
        )

        val music = musicList[pageNum]
        var url = if (music.album.isNotBlank()) music.album else music.url
        val title = music.title
        val artist = music.artist

        AlbumCard(
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alpha
                    scaleX = scale
                    scaleY = scale
                },
            albumUrl = url,
            title = title,
            artist = artist
        )
    }
}