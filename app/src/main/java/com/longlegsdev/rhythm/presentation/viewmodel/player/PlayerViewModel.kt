package com.longlegsdev.rhythm.presentation.viewmodel.player

import androidx.lifecycle.ViewModel
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerManager: MusicPlayerManager,
) : ViewModel() {

    val playbackState = playerManager.playbackState
    val isPlay = playerManager.isPlaying
    val currentIndex = playerManager.currentIndex
    val currentMusic = playerManager.currentMusic
    val musicList = playerManager.musicList
    val currentPosition = playerManager.currentPosition
    val bufferedPosition = playerManager.bufferedPosition
    val duration = playerManager.duration

    fun play(musicList: List<MusicEntity>, index: Int) {
        playerManager.play(musicList, index)
    }

    fun playPause() = playerManager.playPause()

    fun next() = playerManager.next()

    fun previous() = playerManager.previous()

    fun seekTo(positionMs: Long) = playerManager.seekTo(positionMs)

}