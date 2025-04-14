package com.longlegsdev.rhythm.domain.usecase.channel

import com.longlegsdev.rhythm.domain.repository.ChannelRepository
import javax.inject.Inject

class DeleteFavoriteChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(
        channelId: Int
    ) = channelRepository.deleteFavoriteMusic(
        channelId = channelId
    )
}