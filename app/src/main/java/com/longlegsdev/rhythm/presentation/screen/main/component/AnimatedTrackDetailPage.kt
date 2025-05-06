package com.longlegsdev.rhythm.presentation.screen.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.longlegsdev.rhythm.presentation.screen.main.page.TrackDetailPage

@Composable
fun AnimatedTrackDetailPage(
    isVisible: Boolean,
    onTrackDetailPageDismiss: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(durationMillis = 200)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },
            animationSpec = tween(durationMillis = 200)
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        TrackDetailPage(
            onBack = { onTrackDetailPageDismiss(false) },
        )
    }
}