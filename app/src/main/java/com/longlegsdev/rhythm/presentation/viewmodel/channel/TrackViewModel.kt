package com.longlegsdev.rhythm.presentation.viewmodel.channel

import androidx.lifecycle.ViewModel
import com.longlegsdev.rhythm.domain.usecase.track.TrackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TrackViewModel @Inject constructor(
    private val trackUseCase: TrackUseCase,

    ) : ViewModel() {



}