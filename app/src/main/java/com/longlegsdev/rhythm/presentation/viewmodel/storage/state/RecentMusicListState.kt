package com.longlegsdev.rhythm.presentation.viewmodel.storage.state

import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity


data class RecentMusicListState(
    val musics: List<RecentMusicEntity> = emptyList(),
    val isLoading: Boolean = false,
    val onSuccess: Boolean = false,
    val errorMessage: String? = null
)