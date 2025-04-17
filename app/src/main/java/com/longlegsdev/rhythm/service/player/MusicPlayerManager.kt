package com.longlegsdev.rhythm.service.player

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import com.longlegsdev.rhythm.data.entity.MusicEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.getOrNull
import kotlin.collections.map

class MusicPlayerManager @Inject constructor(
    private val musicPlayer: MusicPlayer,
) {
    private val _playbackState = MutableStateFlow<PlaybackState?>(null)
    val playbackState: StateFlow<PlaybackState?> = _playbackState.asStateFlow()

    private val _currentMusic = MutableStateFlow<MusicEntity?>(null)
    val currentMusic: StateFlow<MusicEntity?> = _currentMusic.asStateFlow()

    private val _currentPosition = MutableStateFlow(0)
    val currentPosition: StateFlow<Int> = _currentPosition.asStateFlow()

    private val _duration = MutableStateFlow(0)
    val duration: StateFlow<Int> = _duration.asStateFlow()

    private var currentList: List<MusicEntity> = emptyList()
    private var currentIndex: Int = 0

    private var positionJob: Job? = null

    private val player = musicPlayer.getPlayer()

    init {
        player.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                updateState()
                if (isPlaying) startTrackingPosition() else stopTrackingPosition()
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                updateState()
            }

            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    next()
                }
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

        player.setMediaItems(items, index, 0L)
        player.prepare()
        player.play()

        _currentMusic.value = list[index]
        _duration.value = player.duration.toInt()
    }

    fun playPause() {

        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun next() {
        musicPlayer.next()
        _currentMusic.value = currentList.getOrNull(getPlayer().currentMediaItemIndex)
    }

    fun previous() {
        musicPlayer.previous()
        _currentMusic.value = currentList.getOrNull(getPlayer().currentMediaItemIndex)
    }

    fun seekTo(positionMs: Long) {
        musicPlayer.getPlayer().seekTo(positionMs)
        _currentPosition.value = positionMs.toInt()
    }

    private fun updateState() {
        val player = musicPlayer.getPlayer()
        val currentItem = player.currentMediaItem ?: return
        val index = player.currentMediaItemIndex
        _currentMusic.value = currentList.getOrNull(index)
        _duration.value = player.duration.toInt()
        _playbackState.value = musicPlayer.getState()
    }

    private fun startTrackingPosition() {
        stopTrackingPosition()
        positionJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                _currentPosition.value = musicPlayer.getPlayer().currentPosition.toInt()
                delay(1000L)
            }
        }
    }

    private fun stopTrackingPosition() {
        positionJob?.cancel()
        positionJob = null
    }

    fun getPlayer(): Player = musicPlayer.getPlayer()

}