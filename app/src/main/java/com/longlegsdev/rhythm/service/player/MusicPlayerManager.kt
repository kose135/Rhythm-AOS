package com.longlegsdev.rhythm.service.player

import android.content.Context
import android.content.Intent
import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.HttpDataSource.HttpDataSourceException
import androidx.media3.datasource.HttpDataSource.InvalidResponseCodeException
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.service.RhythmService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MusicPlayerManager @Inject constructor(
    private val context: Context,
    private val musicPlayer: MusicPlayer,
    private val musicUseCase: MusicUseCase,
) : MusicPlayerEventListener {

    private val _playbackState = MutableStateFlow<PlaybackState>(PlaybackState.IDLE)
    val playbackState: StateFlow<PlaybackState> = _playbackState.asStateFlow()

    private val _playbackEvents = MutableSharedFlow<PlaybackEvent>()
    val playbackEvents: SharedFlow<PlaybackEvent> = _playbackEvents.asSharedFlow()

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
        musicPlayer.setMusicPlayerEventListener(this)
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

    fun play(index: Int) {
        musicPlayer.play(index)
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
        if (musicPlayer.hasNext()) {
            musicPlayer.next()
            updateState()
        } else {
            emitPlaybackEvent(PlaybackEvent.EndOfPlaylist)
        }
    }

    fun previous() {
        if (musicPlayer.hasPrevious()) {
            musicPlayer.previous()
            updateState()
        } else {
            emitPlaybackEvent(PlaybackEvent.StartOfPlaylist)
        }
    }

    private fun emitPlaybackEvent(event: PlaybackEvent) {
        CoroutineScope(Dispatchers.Main).launch {
            _playbackEvents.emit(event)
        }
    }

    fun seekTo(positionMs: Long) {
        musicPlayer.seekTo(positionMs)
        _currentPosition.value = musicPlayer.getCurrentPosition()
    }

    private fun updateState() {
        val index = musicPlayer.getCurrentIndex()
        val duration = musicPlayer.getDuration()

        _currentIndex.value = index
        _currentMusic.value = _musicList.value.getOrNull(index) ?: MusicEntity.EMPTY
        _currentPosition.value = 0L
        _bufferedPosition.value = 0L
        _duration.value =
            if (duration < 0L) _currentMusic.value.duration * 1000 else duration // metadata를 가져오지 못하는 상황 발생
        _playbackState.value = musicPlayer.getState()

        Timber.d("change playbackstate: ${_playbackState.value}")

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

    override fun onPlaybackStateChanged(state: PlaybackState) {
        _playbackState.value = state
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        _isPlaying.value = isPlaying
        if (isPlaying) {
            startUpdatingCurrentPosition()
        } else {
            stopUpdatingCurrentPosition()
        }
    }

    override fun onMediaItemChanged(mediaItem: MediaItem?) {
        updateState()
        // 최근 재생곡 추가
        addToRecentlyPlayed(_currentMusic.value)
    }

    override fun onPlayerError(error: PlaybackException) {
        val message = when (val cause = error.cause) {
            is InvalidResponseCodeException -> "서버 오류: ${cause.responseCode}"
            is HttpDataSourceException -> "네트워크 오류 발생"
            else -> "알 수 없는 오류가 발생했습니다"
        }
        emitPlaybackEvent(PlaybackEvent.PlaybackError(message))

        // 다음곡 재생
        next()
    }

    private fun addToRecentlyPlayed(music: MusicEntity) {
        if (music == MusicEntity.EMPTY) return

        CoroutineScope(Dispatchers.IO).launch {
            musicUseCase.addRecent(music)
        }
    }

}