package com.longlegsdev.rhythm.domain.usecase.channel

data class ChannelUseCase(
    val getList: GetChannelListUseCase,
    val getRecommendedList: GetChannelRecommendedUseCase,
    val getMusicList: GetChannelMusicListUseCase,
    val addLike: AddChannelLikeUseCase,
    val delLike: DeleteChannelLikeUseCase,
    val addFavorite: AddFavoriteChannelUseCase,
    val delFavorite: DeleteFavoriteChannelUseCase,
    val getAllFavorite: GetAllFavoriteChannelUseCase,
)
