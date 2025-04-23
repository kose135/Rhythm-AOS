package com.longlegsdev.rhythm.presentation.viewmodel.player.event

import androidx.compose.ui.text.style.TextAlign

sealed class AlignmentEvent(val textAlign: TextAlign) {
    object Left : AlignmentEvent(TextAlign.Left)
    object Center : AlignmentEvent(TextAlign.Center)

    fun isSelected(target: AlignmentEvent): Boolean =
        this::class == target::class
}