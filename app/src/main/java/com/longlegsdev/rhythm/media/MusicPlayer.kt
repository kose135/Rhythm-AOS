package com.longlegsdev.rhythm.media

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayer @Inject constructor(
    private val player: ExoPlayer
) {
    private var currentIndex: Int = 0
    private var playlist: List<MusicEntity> = emptyList()

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

    fun isPlaying(): Boolean = player.isPlaying

    fun getCurrentPosition(): Long = player.currentPosition

    fun getDuration(): Long = player.duration

    fun release() {
        player.release()
    }

}