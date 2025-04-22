package com.longlegsdev.rhythm.service.player

import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException

interface MusicPlayerEventListener {
    fun onPlaybackStateChanged(state: PlaybackState) {}
    fun onIsPlayingChanged(isPlaying: Boolean) {}
    fun onMediaItemChanged(mediaItem: MediaItem?) {}
    fun onPlayerError(error: PlaybackException) {}
}