package com.longlegsdev.rhythm.presentation.viewmodel.main

import androidx.lifecycle.ViewModel
import com.longlegsdev.rhythm.presentation.viewmodel.main.state.MainScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()


    fun setShowMiniPlayer(show: Boolean) {
        _uiState.update { it.copy(showMiniPlayer = show) }
    }

    fun setShowMusicPage(show: Boolean) {
        _uiState.update { it.copy(showMusicPage = show) }
    }

    fun setShowTrackDetailPage(show: Boolean) {
        _uiState.update { it.copy(showTrackDetailPage = show) }
    }

}
