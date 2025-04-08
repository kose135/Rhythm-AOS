package com.longlegsdev.rhythm.service.player

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import com.longlegsdev.rhythm.data.entity.MusicEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.collections.getOrNull
import kotlin.collections.map

class MusicPlayerManager @Inject constructor(
    private val musicPlayer: MusicPlayer,
) {
    private val _playbackState = MutableStateFlow<PlaybackState?>(null)
    val playbackState: StateFlow<PlaybackState?> = _playbackState.asStateFlow()

    private var currentList: List<MusicEntity> = emptyList()
    private var currentIndex: Int = 0

    init {
        musicPlayer.getPlayer().addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateState()
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                updateState()
            }
        })
    }

    fun play(list: List<MusicEntity>, index: Int) {
        currentList = list
        currentIndex = index

        val items = list.map { music ->
            MediaItem.Builder()
                .setUri(music.url)
                .setMediaId(music.id.toString())
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(music.title)
                        .setArtist(music.artist)
                        .build()
                )
                .build()
        }

        musicPlayer.getPlayer().setMediaItems(items, index, 0L)
        musicPlayer.getPlayer().prepare()
        musicPlayer.getPlayer().play()
    }

    fun next() {
        musicPlayer.next()
    }

    fun previous() {
        musicPlayer.previous()
    }

    private fun updateState() {
        val player = musicPlayer.getPlayer()
        val currentItem = player.currentMediaItem
        if (currentItem == null) return

        val currentMusic = currentList.getOrNull(player.currentMediaItemIndex)
        if (currentMusic == null) return

        _playbackState.value = musicPlayer.getState()
    }

    fun getPlayer() = musicPlayer.getPlayer()

}