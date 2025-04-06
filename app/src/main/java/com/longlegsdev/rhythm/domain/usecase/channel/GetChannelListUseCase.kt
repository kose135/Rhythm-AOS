package com.longlegsdev.rhythm.domain.usecase.channel

import com.longlegsdev.rhythm.domain.repository.ChannelRepository
import com.longlegsdev.rhythm.util.Rhythm
import javax.inject.Inject

class GetChannelListUseCase @Inject constructor(
    private val channelRepository: ChannelRepository
){
    suspend operator fun invoke(
        page: Int = Rhythm.DEFAULT_PAGE,
        offset: Int = Rhythm.DEFAULT_OFFSET
    ) = channelRepository.getChannels(
        page = page,
        offset = offset
    )
}