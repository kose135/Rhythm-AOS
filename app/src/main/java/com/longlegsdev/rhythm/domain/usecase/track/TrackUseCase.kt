package com.longlegsdev.rhythm.domain.usecase.track

data class TrackUseCase(
    val getList: GetTrackListUseCase,
    val getRecommendedList: GetRecommendTrackUseCase,
    val getMusicList: GetTrackMusicListUseCase,
    val addFavorite: AddFavoriteTrackUseCase,
    val delFavorite: DeleteFavoriteTrackUseCase,
    val getAllFavorite: GetAllFavoriteTrackUseCase,
)
