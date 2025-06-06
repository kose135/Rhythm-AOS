package com.longlegsdev.rhythm.presentation.viewmodel.common.state

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val onSuccess: Boolean = false,
    val errorMessage: String? = null
)