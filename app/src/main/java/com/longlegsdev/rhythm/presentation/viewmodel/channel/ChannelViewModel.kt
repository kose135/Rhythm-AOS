package com.longlegsdev.rhythm.presentation.viewmodel.channel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.data.remote.model.ChannelList
import com.longlegsdev.rhythm.domain.doOnFailure
import com.longlegsdev.rhythm.domain.doOnLoading
import com.longlegsdev.rhythm.domain.doOnSuccess
import com.longlegsdev.rhythm.domain.usecase.channel.ChannelUseCase
import com.longlegsdev.rhythm.presentation.viewmodel.channel.state.ChannelListState
import com.longlegsdev.rhythm.util.Rhythm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import androidx.compose.runtime.State


@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase,
) : ViewModel() {

    private var currentPage = 1

    private val _channelListState: MutableState<ChannelListState> =
        mutableStateOf(ChannelListState())
    val channelListState: State<ChannelListState> = _channelListState


    init {
        fetchChannelList()
    }

    fun fetchChannelList() {
        viewModelScope.launch {

            channelUseCase.getList(
                page = currentPage,
                offset = Rhythm.DEFAULT_OFFSET
            )
                .doOnSuccess {
                    Timber.d("API Call Success")

                    _channelListState.value = ChannelListState(channels = it.channels + it.channels + it.channels + it.channels)
                }
                .doOnFailure {
                    Timber.d("API Call Failed: ${it.localizedMessage}")
                    _channelListState.value = ChannelListState(errorMessage = it.localizedMessage)

                }
                .doOnLoading {
                    _channelListState.value = ChannelListState(isLoading = true)
                }.collect()
        }
    }

}