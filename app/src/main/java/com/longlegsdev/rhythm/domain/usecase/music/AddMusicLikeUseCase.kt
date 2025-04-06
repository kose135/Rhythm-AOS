package com.longlegsdev.rhythm.domain.usecase.music

import com.longlegsdev.rhythm.domain.repository.MusicRepository
import javax.inject.Inject

class AddMusicLikeUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(
        musicId: Int
    ) = musicRepository.addMusicLike(
        musicId = musicId
    )
}