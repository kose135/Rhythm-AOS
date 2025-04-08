package com.longlegsdev.rhythm.presentation.viewmodel.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.domain.doOnFailure
import com.longlegsdev.rhythm.domain.doOnLoading
import com.longlegsdev.rhythm.domain.doOnSuccess
import com.longlegsdev.rhythm.domain.usecase.channel.ChannelUseCase
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.presentation.viewmodel.channel.state.ChannelListState
import com.longlegsdev.rhythm.presentation.viewmodel.main.state.MusicListState
import com.longlegsdev.rhythm.util.Rhythm
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.collections.plusAssign


@HiltViewModel
class HomeViewModel @Inject constructor(
//    arguments: SavedStateHandle,
    private val channelUseCase: ChannelUseCase,
    private val musicUseCase: MusicUseCase,
) : ViewModel() {

    private var channelList: ArrayList<ChannelEntity> = arrayListOf()
    private var musicList: ArrayList<MusicEntity> = arrayListOf()

    private val _channelListState: MutableState<ChannelListState> =
        mutableStateOf(ChannelListState())
    val channelListState: State<ChannelListState> = _channelListState

    private val _musicListState: MutableState<MusicListState> =
        mutableStateOf(MusicListState())
    val musicListState: State<MusicListState> = _musicListState

    init {
        fetchChannelRecommended()
        fetchMusicBest()
    }

    fun fetchChannelRecommended() {
        viewModelScope.launch {
            val limit = Rhythm.DEFAULT_LIMIT

            channelUseCase.getRecommendedList(
                limit = limit
            )
                .doOnSuccess {
                    Timber.d("API Call Success")

                    channelList += it.channels
                    _channelListState.value = _channelListState.value.copy(channels = channelList, isLoading = false)
                }
                .doOnFailure {
                    Timber.d("API Call Failed: ${it.localizedMessage}")
                    _channelListState.value =
                        ChannelListState(errorMessage = it.localizedMessage, channels = channelList, isLoading = false)

                }
                .doOnLoading {
                    _channelListState.value = _channelListState.value.copy(isLoading = true)
                }.collect()
        }
    }

    fun fetchMusicBest() {
        viewModelScope.launch {
            val limit = Rhythm.DEFAULT_LIMIT

            musicUseCase.getBestList(
                limit = limit
            )
                .doOnSuccess {
                    Timber.d("API Call Success")

                    musicList += it.musics
                    _musicListState.value =
                        _musicListState.value.copy(musics = musicList.toList(), errorMessage = null, isLoading = false)
                }
                .doOnFailure {
                    Timber.d("API Call Failed: ${it.localizedMessage}")
                    _musicListState.value = MusicListState(errorMessage = it.localizedMessage, isLoading = false)

                }
                .doOnLoading {
                    _musicListState.value =
                        _musicListState.value.copy(isLoading = true, errorMessage = null)
                }.collect()

        }
    }

}