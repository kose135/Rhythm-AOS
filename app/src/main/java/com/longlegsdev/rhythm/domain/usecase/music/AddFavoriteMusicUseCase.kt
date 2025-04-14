package com.longlegsdev.rhythm.domain.usecase.music

import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.domain.repository.ChannelRepository
import com.longlegsdev.rhythm.domain.repository.MusicRepository
import javax.inject.Inject

class AddFavoriteMusicUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(
        music: MusicEntity
    ) = musicRepository.addFavoriteMusic(
        music = music
    )
}