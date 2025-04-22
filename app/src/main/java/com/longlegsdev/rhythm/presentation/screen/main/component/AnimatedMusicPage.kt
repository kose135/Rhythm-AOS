package com.longlegsdev.rhythm.presentation.screen.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.longlegsdev.rhythm.presentation.screen.main.page.MusicPage

@Composable
fun AnimatedMusicPage(
    isVisible: Boolean,
    onSwipeDown: () -> Unit,
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
        MusicPage(
            onSwipeDown = {onSwipeDown }
        )
    }
}