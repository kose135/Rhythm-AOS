package com.longlegsdev.rhythm.presentation.viewmodel.channel

import androidx.lifecycle.ViewModel
import com.longlegsdev.rhythm.domain.usecase.channel.ChannelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class TrackViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase,

) : ViewModel() {



}