package com.longlegsdev.rhythm.domain.usecase.music

import com.longlegsdev.rhythm.domain.repository.MusicRepository
import javax.inject.Inject

class IsFavoriteMusicUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(
        musicId: Int
    ) = musicRepository.isFavoritedMusic(
        musicId = musicId
    )
}