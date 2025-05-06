package com.longlegsdev.rhythm.presentation.viewmodel.track

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.TrackEntity
import com.longlegsdev.rhythm.domain.doOnFailure
import com.longlegsdev.rhythm.domain.doOnLoading
import com.longlegsdev.rhythm.domain.doOnSuccess
import com.longlegsdev.rhythm.domain.usecase.track.TrackUseCase
import com.longlegsdev.rhythm.presentation.viewmodel.common.state.UiState
import com.longlegsdev.rhythm.util.Rhythm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class TrackViewModel @Inject constructor(
    private val trackUseCase: TrackUseCase,
) : ViewModel() {

    private val _isFavoriteTrack = MutableStateFlow(false)
    val isFavoriteTrack: StateFlow<Boolean> = _isFavoriteTrack.asStateFlow()

    private val _recommendTrackState: MutableState<UiState<List<TrackEntity>>> =
        mutableStateOf(UiState<List<TrackEntity>>())
    val recommendTrackState: State<UiState<List<TrackEntity>>> = _recommendTrackState

    private val _trackState: MutableState<UiState<List<TrackEntity>>> =
        mutableStateOf(UiState<List<TrackEntity>>())
    val trackListState: State<UiState<List<TrackEntity>>> = _trackState

    private val _trackEntityInfo = MutableStateFlow(TrackEntity.EMPTY)
    val trackEntityInfo: StateFlow<TrackEntity> = _trackEntityInfo.asStateFlow()

    private val _trackMusicState: MutableState<UiState<List<MusicEntity>>> =
        mutableStateOf(UiState<List<MusicEntity>>())
    val trackMusicState: State<UiState<List<MusicEntity>>> = _trackMusicState

    private var currentPage = 1

    init {
        fetchRecommendedTrack()
        fetchTrackList()
    }

    fun fetchRecommendedTrack() {
        viewModelScope.launch {
            val limit = Rhythm.DEFAULT_LIMIT

            trackUseCase.getRecommendedList(limit = limit)
                .doOnSuccess {
                    _recommendTrackState.value = UiState(onSuccess = true, data = it.tracks)
                }
                .doOnFailure {
                    _recommendTrackState.value = UiState(errorMessage = it.localizedMessage)
                    Timber.d("API Call Failed: ${it.localizedMessage}")
                }
                .doOnLoading {
                    _recommendTrackState.value = UiState(isLoading = true)
                }.collect()
        }
    }


    fun fetchTrackList() {
        viewModelScope.launch {
            trackUseCase.getList(page = currentPage, offset = Rhythm.DEFAULT_OFFSET)
                .doOnSuccess {
                    _trackState.value = UiState(
                        onSuccess = true,
                        data = it.tracks + it.tracks + it.tracks + it.tracks // Dummy for test
                    )
                }
                .doOnFailure {
                    _trackState.value = UiState(errorMessage = it.localizedMessage)
                }
                .doOnLoading {
                    _trackState.value = UiState(isLoading = true)
                }.collect()
        }
    }

    fun setTrack(track: TrackEntity) {
        _trackEntityInfo.value = track
    }

    fun getMusicList(track: TrackEntity) {
        viewModelScope.launch {
            val trackId = track.id

            setTrack(track)
            checkFavoriteTrack(track.id)

            trackUseCase.getMusicList(trackId)
                .doOnSuccess {
                    _trackMusicState.value = UiState(onSuccess = true, data = it.musicList)
                }
                .doOnFailure {
                    _trackMusicState.value =
                        UiState(errorMessage = it.localizedMessage, isLoading = false)
                }
                .doOnLoading {
                    _trackMusicState.value = UiState(isLoading = true)
                }.collect()
        }
    }

    fun checkFavoriteTrack(trackId: Int) {
        viewModelScope.launch {
            trackUseCase.isFavoritedTrack(trackId)
                .collect {
                    _isFavoriteTrack.value = it
                }
        }
    }

    fun toggleFavoriteTrack(trackId: Int) {
        if (_isFavoriteTrack.value) {
            deleteFavoriteTrack(trackId)
        } else {
            addFavoriteTrack(trackId)
        }

        checkFavoriteTrack(trackId)
    }

    fun addFavoriteTrack(trackId: Int) {
        viewModelScope.launch {
            trackUseCase.addFavorite(trackId)
        }
    }

    fun deleteFavoriteTrack(trackId: Int) {
        viewModelScope.launch {
            trackUseCase.delFavorite(trackId)
        }
    }

}