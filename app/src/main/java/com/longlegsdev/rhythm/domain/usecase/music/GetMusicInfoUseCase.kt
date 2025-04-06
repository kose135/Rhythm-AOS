package com.longlegsdev.rhythm.domain.usecase.music

import com.longlegsdev.rhythm.domain.repository.MusicRepository
import com.longlegsdev.rhythm.util.Rhythm
import javax.inject.Inject

class GetMusicInfoUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(
        musicId: Int
    ) = musicRepository.getMusicInfo(
        musicId = musicId
    )
}