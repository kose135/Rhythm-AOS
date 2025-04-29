package com.longlegsdev.rhythm.domain.usecase.music

data class MusicUseCase(
    val getInfo: GetMusicInfoUseCase,
    val getBestList: GetMusicBestListUseCase,
    val getRecentList: GetRecentMusicListUseCase,
    val addRecent: AddRecentMusicUseCase,
    val addFavorite: AddFavoriteMusicUseCase,
    val delFavorite: DeleteFavoriteMusicUseCase,
    val getAllFavorite: GetAllFavoriteMusicUseCase,
)