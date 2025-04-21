package com.longlegsdev.rhythm.service.player

import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.service.RhythmService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.getOrNull

class MusicPlayerManager @Inject constructor(
    private val context: Context,
    private val musicPlayer: MusicPlayer,
) {

    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.IDLE)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _isPlaying = MutableStateFlow<Boolean>(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _currentMusic = MutableStateFlow(MusicEntity.EMPTY)
    val currentMusic: StateFlow<MusicEntity> = _currentMusic.asStateFlow()

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition.asStateFlow()

    private val _bufferedPosition = MutableStateFlow(0L)
    val bufferedPosition: StateFlow<Long> = _bufferedPosition.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()

    private val _musicList = MutableStateFlow(emptyList<MusicEntity>())
    val musicList: StateFlow<List<MusicEntity>> = _musicList.asStateFlow()

    private var positionJob: Job? = null

    init {
        observePlaybackEvents()
        observeMediaItemChanged()
    }

    @OptIn(UnstableApi::class)
    fun play(list: List<MusicEntity>, index: Int) {
        val serviceIntent = Intent(context, RhythmService::class.java)
        context.startForegroundService(serviceIntent)

        _musicList.value = list
        _currentIndex.value = index

        musicPlayer.setMediaItems(list, index)
        musicPlayer.play()

        updateState()
    }

    fun playPause() {
        if (isPlaying.value) {
            musicPlayer.pause()
        } else {
            musicPlayer.play()
        }
        updateState()
    }

    fun next() {
        musicPlayer.next()
        updateState()
    }

    fun previous() {
        musicPlayer.previous()
        updateState()
    }

    fun seekTo(positionMs: Long) {
        musicPlayer.seekTo(positionMs)
//        _currentPosition.value = positionMs
        _currentPosition.value = musicPlayer.getCurrentPosition()
    }

    private fun updateState() {
        val index = musicPlayer.getCurrentIndex()
        val duration = musicPlayer.getDuration()

        _currentIndex.value = index
        _currentMusic.value = _musicList.value.getOrNull(index) ?: MusicEntity.EMPTY
        _duration.value = if (duration < 0L) _currentMusic.value.duration * 1000 else duration // metadata를 가져오지 못하는 상황 발생
        _playbackState.value = musicPlayer.getState()
    }


    fun startUpdatingCurrentPosition() {
        if (positionJob?.isActive == true) return

        positionJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                if (_isPlaying.value && _playbackState.value == PlaybackState.PLAYING) {
                    val position = musicPlayer.getCurrentPosition()
                    val buffer = musicPlayer.getBuffer()

                    _currentPosition.value = position
                    _bufferedPosition.value = buffer

                    val delayMs = 1000 - (position % 1000) + 100;
                    delay(delayMs) // 1초마다 업데이트
                } else {
                    delay(1000L)
                }

            }
        }
    }

    fun stopUpdatingCurrentPosition() {
        positionJob?.cancel()
    }

    fun getPlayer(): Player = musicPlayer.getPlayer()

    private fun observePlaybackEvents() {
        musicPlayer.setOnPlaybackStateChangedListener { newState ->
            _playbackState.value = newState
        }

        musicPlayer.setOnIsPlayingChangedListener { isPlaying ->
            _isPlaying.value = isPlaying
            if (isPlaying) {
                startUpdatingCurrentPosition()
            } else {
                stopUpdatingCurrentPosition()
            }
        }
    }

    private fun observeMediaItemChanged() {
        musicPlayer.setOnMediaItemChangedListener {
            updateState()
        }
    }
}