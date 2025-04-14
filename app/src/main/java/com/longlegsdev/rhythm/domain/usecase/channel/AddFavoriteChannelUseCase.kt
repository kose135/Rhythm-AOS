package com.longlegsdev.rhythm.domain.usecase.channel

import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.domain.repository.ChannelRepository
import javax.inject.Inject

class AddFavoriteChannelUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
) {
    suspend operator fun invoke(
        channel: ChannelEntity
    ) = channelRepository.addFavoriteChannel(
        channel = channel
    )
}