package com.longlegsdev.rhythm.presentation.viewmodel.storage

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.longlegsdev.rhythm.data.entity.FavoriteChannelEntity
import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity
import com.longlegsdev.rhythm.domain.doOnFailure
import com.longlegsdev.rhythm.domain.doOnLoading
import com.longlegsdev.rhythm.domain.doOnSuccess
import com.longlegsdev.rhythm.domain.usecase.channel.ChannelUseCase
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.presentation.viewmodel.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class StorageViewModel @Inject constructor(
    private val channelUseCase: ChannelUseCase,
    private val musicUseCase: MusicUseCase,
) : ViewModel() {

    private val _recentMusicListState: MutableState<UiState<List<RecentMusicEntity>>> =
        mutableStateOf(UiState<List<RecentMusicEntity>>())
    val recentMusicListState: State<UiState<List<RecentMusicEntity>>> = _recentMusicListState

    private val _favoriteMusicListState: MutableState<UiState<List<FavoriteMusicEntity>>> =
        mutableStateOf(UiState<List<FavoriteMusicEntity>>())
    val favoriteMusicListState: State<UiState<List<FavoriteMusicEntity>>> = _favoriteMusicListState

    private val _favoriteChannelListState: MutableState<UiState<List<FavoriteChannelEntity>>> =
        mutableStateOf(UiState<List<FavoriteChannelEntity>>())
    val favoriteChannelListState: State<UiState<List<FavoriteChannelEntity>>> =
        _favoriteChannelListState

    init {
        fetchRecentMusicList()
        fetchFavoriteMusicList()
        fetchFavoriteChannelList()
    }


    fun fetchRecentMusicList() {
        viewModelScope.launch {

            musicUseCase.getRecentList()
                .doOnSuccess {
                    Timber.d("Get data from database")

                    _recentMusicListState.value =
                        UiState<List<RecentMusicEntity>>(onSuccess = true, data = it)
                }
                .doOnFailure {
                    Timber.d("Failed to retrieve data from database")

                    _recentMusicListState.value =
                        UiState<List<RecentMusicEntity>>(errorMessage = it.localizedMessage)
                }
                .doOnLoading {
                    Timber.d("loading...")

                }.collect()
        }
    }

    fun fetchFavoriteMusicList() {
        viewModelScope.launch {

            musicUseCase.getAllFavorite()
                .doOnSuccess {
                    Timber.d("Get data from database")

                    _favoriteMusicListState.value =
                        UiState<List<FavoriteMusicEntity>>(onSuccess = true, data = it)
                }
                .doOnFailure {
                    Timber.d("Failed to retrieve data from database")

                    _favoriteMusicListState.value =
                        UiState<List<FavoriteMusicEntity>>(errorMessage = it.localizedMessage)
                }
                .doOnLoading {


                }.collect()
        }
    }

    fun fetchFavoriteChannelList() {
        viewModelScope.launch {

            channelUseCase.getAllFavorite()
                .doOnSuccess {
                    Timber.d("Get data from database")

                    _favoriteChannelListState.value =
                        UiState<List<FavoriteChannelEntity>>(onSuccess = true, data = it)
                }
                .doOnFailure {
                    Timber.d("Failed to retrieve data from database")

                    _favoriteChannelListState.value =
                        UiState<List<FavoriteChannelEntity>>(errorMessage = it.localizedMessage)
                }
                .doOnLoading {

                }.collect()
        }
    }

}