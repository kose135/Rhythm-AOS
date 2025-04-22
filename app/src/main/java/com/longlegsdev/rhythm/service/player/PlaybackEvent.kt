package com.longlegsdev.rhythm.service.player

sealed class PlaybackEvent {
    object EndOfPlaylist : PlaybackEvent()
    object StartOfPlaylist : PlaybackEvent()
    data class PlaybackError(val message: String) : PlaybackEvent()
}