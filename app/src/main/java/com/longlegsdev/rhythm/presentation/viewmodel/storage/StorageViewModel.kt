package com.longlegsdev.rhythm.presentation.viewmodel.storage

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
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.presentation.viewmodel.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StorageViewModel @Inject constructor(
    private val trackUseCase: TrackUseCase,
    private val musicUseCase: MusicUseCase,
) : ViewModel() {

    private val _recentMusicListState: MutableState<UiState<List<MusicEntity>>> =
        mutableStateOf(UiState<List<MusicEntity>>())
    val recentMusicListState: State<UiState<List<MusicEntity>>> = _recentMusicListState

    private val _favoriteMusicListState: MutableState<UiState<List<MusicEntity>>> =
        mutableStateOf(UiState<List<MusicEntity>>())
    val favoriteMusicListState: State<UiState<List<MusicEntity>>> = _favoriteMusicListState

    private val _favoriteTrackListState: MutableState<UiState<List<TrackEntity>>> =
        mutableStateOf(UiState<List<TrackEntity>>())
    val favoriteTrackListState: State<UiState<List<TrackEntity>>> =
        _favoriteTrackListState

    init {
        fetchRecentMusicList()
        fetchFavoriteMusicList()
        fetchFavoriteTrackList()
    }

    fun fetchRecentMusicList() {
        viewModelScope.launch {
            musicUseCase.getRecentList()
                .doOnSuccess { list ->
                    _recentMusicListState.value =
                        UiState<List<MusicEntity>>(onSuccess = true, data = list)
                }
                .doOnFailure {
                    _recentMusicListState.value =
                        UiState(errorMessage = it.localizedMessage, isLoading = false)
                }
                .doOnLoading {
                    val data = _recentMusicListState.value.data
                    if (data == null || data.isEmpty()) {
                        _recentMusicListState.value = UiState(isLoading = true)
                    }
                }.collect()
        }
    }

    fun fetchFavoriteMusicList() {
        viewModelScope.launch {
            musicUseCase.getAllFavorite()
                .doOnSuccess { list ->
                    _favoriteMusicListState.value = UiState(onSuccess = true, data = list)
                }
                .doOnFailure {
                    _favoriteMusicListState.value =
                        UiState(errorMessage = it.localizedMessage, isLoading = false)
                }
                .doOnLoading {
                    val data = _favoriteMusicListState.value.data
                    if (data == null || data.isEmpty()) {
                        _favoriteMusicListState.value = UiState(isLoading = true)
                    }
                }.collect()
        }
    }

    fun fetchFavoriteTrackList() {
        viewModelScope.launch {
            trackUseCase.getAllFavorite()
                .doOnSuccess { list ->
                    _favoriteTrackListState.value = UiState(onSuccess = true, data = list)
                }
                .doOnFailure {
                    _favoriteTrackListState.value =
                        UiState(errorMessage = it.localizedMessage, isLoading = false)
                }
                .doOnLoading {
                    val data = _favoriteTrackListState.value.data
                    if (data == null || data.isEmpty()) {
                        _favoriteTrackListState.value = UiState(isLoading = true)
                    }
                }.collect()
        }
    }

}