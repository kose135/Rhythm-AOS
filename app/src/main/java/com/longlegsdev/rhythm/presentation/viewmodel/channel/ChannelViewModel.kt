package com.longlegsdev.rhythm.presentation.viewmodel.channel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.domain.doOnFailure
import com.longlegsdev.rhythm.domain.doOnLoading
import com.longlegsdev.rhythm.domain.doOnSuccess
import com.longlegsdev.rhythm.domain.usecase.channel.ChannelUseCase
import com.longlegsdev.rhythm.util.Rhythm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import androidx.compose.runtime.State
import com.longlegsdev.rhythm.presentation.viewmodel.state.UiState
import com.longlegsdev.rhythm.service.player.MusicPlayerManager


@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase,
    private val musicPlayerManager: MusicPlayerManager,
) : ViewModel() {

    private var currentPage = 1

    private val _channelListState: MutableState<UiState<List<ChannelEntity>>> =
        mutableStateOf(UiState<List<ChannelEntity>>())
    val channelListState: State<UiState<List<ChannelEntity>>> = _channelListState


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

                    _channelListState.value = UiState<List<ChannelEntity>>(
                        onSuccess = true,
                        data = it.channels + it.channels + it.channels + it.channels
                    )
                }
                .doOnFailure {
                    Timber.d("API Call Failed: ${it.localizedMessage}")
                    _channelListState.value =
                        UiState<List<ChannelEntity>>(errorMessage = it.localizedMessage)

                }
                .doOnLoading {
                    _channelListState.value = UiState<List<ChannelEntity>>(isLoading = true)
                }.collect()
        }
    }

    fun playChannel(channelId: Int) {
        viewModelScope.launch {
            channelUseCase.getMusicList(channelId)
                .doOnSuccess {
                    Timber.d("API Call Success")
                    musicPlayerManager.play(it.musicList, 0)
                }
                .doOnFailure {
                    Timber.d("API Call Failed: ${it.localizedMessage}")
                }
                .doOnLoading {
                    Timber.d("Loading...")
                }.collect()
        }
    }

}