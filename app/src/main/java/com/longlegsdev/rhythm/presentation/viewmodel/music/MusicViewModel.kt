package com.longlegsdev.rhythm.presentation.viewmodel.music

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.domain.doOnFailure
import com.longlegsdev.rhythm.domain.doOnLoading
import com.longlegsdev.rhythm.domain.doOnSuccess
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.presentation.viewmodel.common.state.UiState
import com.longlegsdev.rhythm.util.Rhythm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MusicViewModel @Inject constructor(
    private val musicUseCase: MusicUseCase,
) : ViewModel() {

    private val _bestMusicState: MutableState<UiState<List<MusicEntity>>> =
        mutableStateOf(UiState<List<MusicEntity>>())
    val bestMusicState: State<UiState<List<MusicEntity>>> = _bestMusicState

    private val _isFavoriteMusic = MutableStateFlow(false)
    val isFavoriteMusic: StateFlow<Boolean> = _isFavoriteMusic.asStateFlow()

    init {
        fetchBestMusic()
    }

    fun fetchBestMusic() {
        viewModelScope.launch {
            val limit = Rhythm.DEFAULT_LIMIT

            musicUseCase.getBestList(limit = limit)
                .doOnSuccess {
                    _bestMusicState.value = UiState(onSuccess = true, data = it.musicList)
                }
                .doOnFailure {
                    _bestMusicState.value =
                        UiState(errorMessage = it.localizedMessage, isLoading = false)
                }
                .doOnLoading {
                    _bestMusicState.value = UiState(isLoading = true)
                }.collect()
        }
    }

    fun checkFavoriteMusic(musicId: Int) {
        viewModelScope.launch {
            musicUseCase.isFavoritedMusic(musicId)
                .collect {
                    _isFavoriteMusic.value = it
                }
        }
    }

    fun toggleFavoriteMusic(musicId: Int) {
        if (_isFavoriteMusic.value) {
            deleteFavoriteMusic(musicId)
        } else {
            addFavoriteMusic(musicId)
        }

        checkFavoriteMusic(musicId)
    }

    fun addFavoriteMusic(musicId: Int) {
        viewModelScope.launch {
            musicUseCase.addFavorite(musicId)
        }
    }

    fun deleteFavoriteMusic(musicId: Int) {
        viewModelScope.launch {
            musicUseCase.delFavorite(musicId)
        }
    }

}