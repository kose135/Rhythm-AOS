package com.longlegsdev.rhythm.presentation.screen.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.longlegsdev.rhythm.presentation.screen.main.section.main.MiniPlayBarSection
import com.longlegsdev.rhythm.presentation.screen.main.state.PlayerState

@Composable
fun AnimatedMusicMiniPlayer(
    isVisible: Boolean,
    bottomPadding: Dp,
    playerState: PlayerState,
    onPlayPauseClick: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(durationMillis = 200)
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(durationMillis = 200)
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        MiniPlayBarSection(
            height = bottomPadding,
            isPlay = playerState.isPlaying,
            imageUrl = playerState.music.url,
            title = playerState.music.title,
            artist = playerState.music.artist,
            currentPosition = playerState.currentPosition,
            bufferedPosition = playerState.bufferedPosition,
            duration = playerState.duration,
            onPlayPauseClick = onPlayPauseClick,
            onMiniPlayerBarClick = onClick
        )
    }
}