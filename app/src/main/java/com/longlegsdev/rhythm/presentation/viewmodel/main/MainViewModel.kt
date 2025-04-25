package com.longlegsdev.rhythm.presentation.viewmodel.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.domain.doOnFailure
import com.longlegsdev.rhythm.domain.doOnLoading
import com.longlegsdev.rhythm.domain.doOnSuccess
import com.longlegsdev.rhythm.domain.usecase.channel.ChannelUseCase
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.presentation.viewmodel.main.state.MainScreenUiState
import com.longlegsdev.rhythm.presentation.viewmodel.state.UiState
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
import com.longlegsdev.rhythm.service.player.PlaybackState
import com.longlegsdev.rhythm.util.Rhythm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase,
    private val musicUseCase: MusicUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    private val _recommendTrackListState: MutableState<UiState<List<ChannelEntity>>> =
        mutableStateOf(UiState<List<ChannelEntity>>())
    val recommendTrackListState: State<UiState<List<ChannelEntity>>> = _recommendTrackListState

    private val _bestMusicListState: MutableState<UiState<List<MusicEntity>>> =
        mutableStateOf(UiState<List<MusicEntity>>())
    val bestMusicListState: State<UiState<List<MusicEntity>>> = _bestMusicListState

    private val _trackListState: MutableState<UiState<List<ChannelEntity>>> =
        mutableStateOf(UiState<List<ChannelEntity>>())
    val trackListState: State<UiState<List<ChannelEntity>>> = _trackListState

    private val _musicListState: MutableState<UiState<List<MusicEntity>>> =
        mutableStateOf(UiState<List<MusicEntity>>())
    val musicListState: State<UiState<List<MusicEntity>>> = _musicListState

    private val _trackInfo = MutableStateFlow(ChannelEntity.EMPTY)
    val trackInfo: StateFlow<ChannelEntity> = _trackInfo.asStateFlow()

    private var currentPage = 1

    init {
        fetchRecommendedTrack()
        fetchBestMusic()
        fetchTrackList()
    }

    fun fetchRecommendedTrack() {
        viewModelScope.launch {
            val limit = Rhythm.DEFAULT_LIMIT

            channelUseCase.getRecommendedList(limit = limit)
                .doOnSuccess {
                    _recommendTrackListState.value = UiState(onSuccess = true, data = it.channels)
                }
                .doOnFailure {
                    _recommendTrackListState.value = UiState(errorMessage = it.localizedMessage)
                    Timber.d("API Call Failed: ${it.localizedMessage}")
                }
                .doOnLoading {
                    _recommendTrackListState.value = UiState(isLoading = true)
                }.collect()
        }
    }

    fun fetchBestMusic() {
        viewModelScope.launch {
            val limit = Rhythm.DEFAULT_LIMIT

            musicUseCase.getBestList(limit = limit)
                .doOnSuccess {
                    _bestMusicListState.value = UiState(onSuccess = true, data = it.musicList)
                }
                .doOnFailure {
                    _bestMusicListState.value = UiState(errorMessage = it.localizedMessage, isLoading = false)
                }
                .doOnLoading {
                    _bestMusicListState.value = UiState(isLoading = true)
                }.collect()
        }
    }

    fun fetchTrackList() {
        viewModelScope.launch {
            channelUseCase.getList(page = currentPage, offset = Rhythm.DEFAULT_OFFSET)
                .doOnSuccess {
                    _trackListState.value = UiState(
                        onSuccess = true,
                        data = it.channels + it.channels + it.channels + it.channels // Dummy for test
                    )
                }
                .doOnFailure {
                    _trackListState.value = UiState(errorMessage = it.localizedMessage)
                }
                .doOnLoading {
                    _trackListState.value = UiState(isLoading = true)
                }.collect()
        }
    }

    fun setShowMiniPlayer(show: Boolean) {
        _uiState.update { it.copy(showMiniPlayer = show) }
    }

    fun setShowMusicPage(show: Boolean) {
        _uiState.update { it.copy(showMusicPage = show) }
    }

    fun setShowTrackDetailPage(show: Boolean) {
        _uiState.update { it.copy(showTrackDetailPage = show) }
    }

    fun setTrack(track: ChannelEntity) {
        _trackInfo.value = track
    }

    fun getMusicList(track: ChannelEntity) {
        viewModelScope.launch {
            setTrack(track)
            setShowTrackDetailPage(true)
            val trackId = track.id

            channelUseCase.getMusicList(trackId)
                .doOnSuccess {
                    _musicListState.value = UiState(onSuccess = true, data = it.musicList)
                }
                .doOnFailure {
                    _musicListState.value = UiState(errorMessage = it.localizedMessage, isLoading = false)
                }
                .doOnLoading {
                    _musicListState.value = UiState(isLoading = true)
                }.collect()
        }
    }

    fun addFavoriteMusic() {

    }

    fun deleteFavoriteMusic() {

    }

    fun addLikeMusic() {

    }

    fun deleteLikeMusic() {

    }

    fun addFavoriteTrack() {

    }

    fun deleteFavoriteTrack() {

    }

    fun addLikeTrack() {

    }

    fun deleteLikeTrack() {

    }

}
