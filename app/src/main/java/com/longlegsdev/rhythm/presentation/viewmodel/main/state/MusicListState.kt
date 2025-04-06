package com.longlegsdev.rhythm.presentation.viewmodel.main.state

import com.longlegsdev.rhythm.data.entity.MusicEntity


data class MusicListState(
    val musics: List<MusicEntity> = emptyList(),
    val isLoading: Boolean = false,
    val onSuccess: Boolean = false,
    val errorMessage: String? = null
)