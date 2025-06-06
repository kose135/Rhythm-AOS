package com.longlegsdev.rhythm.presentation.viewmodel.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.presentation.viewmodel.player.state.PlayerUiState
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
import com.longlegsdev.rhythm.service.player.PlaybackEvent
import com.longlegsdev.rhythm.service.player.PlaybackState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val musicPlayerManager: MusicPlayerManager
) : ViewModel() {

    val playbackState = musicPlayerManager.playbackState
    val currentMusic: StateFlow<MusicEntity> = musicPlayerManager.currentMusic
    val musicList: StateFlow<List<MusicEntity>> = musicPlayerManager.musicList
    val currentIndex: StateFlow<Int> = musicPlayerManager.currentIndex
    val playbackEvents: SharedFlow<PlaybackEvent> = musicPlayerManager.playbackEvents

    val playerState: StateFlow<PlayerUiState> = combine(
        listOf(
            musicPlayerManager.playbackState,
            musicPlayerManager.currentMusic,
            musicPlayerManager.isPlaying,
            musicPlayerManager.currentPosition,
            musicPlayerManager.bufferedPosition,
            musicPlayerManager.duration
        )
    ) { values ->
        PlayerUiState(
            playbackState = values[0] as PlaybackState,
            music = values[1] as MusicEntity,
            isPlaying = values[2] as Boolean,
            currentPosition = values[3] as Long,
            bufferedPosition = values[4] as Long,
            duration = values[5] as Long
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PlayerUiState()
    )

    fun playPause() {
        musicPlayerManager.playPause()
    }

    fun seekTo(position: Long) {
        musicPlayerManager.seekTo(position)
    }

    fun play(index: Int) {
        musicPlayerManager.play(index)
    }

    fun play(musicList: List<MusicEntity>, index: Int) {
        musicPlayerManager.play(musicList, index)
    }

    fun next() {
        musicPlayerManager.next()
    }

    fun previous() {
        musicPlayerManager.previous()
    }
}