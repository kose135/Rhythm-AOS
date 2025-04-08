package com.longlegsdev.rhythm.service.player

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.longlegsdev.rhythm.service.player.PlaybackState
import com.longlegsdev.rhythm.data.entity.MusicEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayer @Inject constructor(
    private val player: ExoPlayer
) : Player.Listener {

    private var currentIndex: Int = 0
    private var playlist: List<MusicEntity> = emptyList()
    private var playerState = PlaybackState.IDLE

    private var playbackStateListener: ((PlaybackState) -> Unit)? = null
    private var currentMediaItemListener: ((MediaItem?) -> Unit)? = null

    init {
        setup()
    }

    private fun setup() {
        player.addListener(this)
    }

    fun setPlaylist(
        musics: List<MusicEntity>,
        startIndex: Int = 0
    ) {
        playlist = musics
        currentIndex = startIndex
        player.clearMediaItems()

        musics.forEach { music ->
            player.addMediaItem(MediaItem.fromUri(music.url))
        }

        player.seekTo(startIndex, 0)
        player.prepare()
    }

    fun play() {
        player.playWhenReady = true
    }

    fun play(
        musics: List<MusicEntity>,
        startIndex: Int = 0
    ) {
        playlist = musics
        currentIndex = startIndex
        player.clearMediaItems()

        musics.forEach { music ->
            player.addMediaItem(MediaItem.fromUri(music.url))
        }

        player.seekTo(startIndex, 0)
        player.prepare()

        play()
    }

    fun pause() {
        player.playWhenReady = false
    }

    fun next() {
        if (currentIndex < playlist.size - 1) {
            currentIndex++
            player.seekTo(currentIndex, 0)
            play()
        }
    }

    fun previous() {
        if (currentIndex > 0) {
            currentIndex--

            player.seekTo(currentIndex, 0)
            play()
        }
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    fun isPlaying(): Boolean = player.isPlaying

    fun getCurrentPosition(): Long = player.currentPosition

    fun getDuration(): Long = player.duration

    fun release() {
        player.release()
    }

    fun getState() = playerState

    fun getPlayer(): ExoPlayer = player

    override fun onPlaybackStateChanged(state: Int) {
        playerState = when (state) {
            Player.STATE_IDLE -> PlaybackState.IDLE
            Player.STATE_BUFFERING -> PlaybackState.BUFFERING
            Player.STATE_READY -> {
                if (player.isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED
            }

            Player.STATE_ENDED -> PlaybackState.ENDED
            else -> PlaybackState.IDLE
        }

        playbackStateListener?.invoke(playerState)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        currentMediaItemListener?.invoke(mediaItem)
    }
}