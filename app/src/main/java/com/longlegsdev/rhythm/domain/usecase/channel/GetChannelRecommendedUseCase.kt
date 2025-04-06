package com.longlegsdev.rhythm.domain.usecase.channel

import com.longlegsdev.rhythm.domain.repository.ChannelRepository
import javax.inject.Inject

class GetChannelRecommendedUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(
        limit: Int = 10
    ) = channelRepository.getChannelRecommended(
        limit = limit
    )
}