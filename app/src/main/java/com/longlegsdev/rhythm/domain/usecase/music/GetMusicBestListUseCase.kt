package com.longlegsdev.rhythm.domain.usecase.music

import com.longlegsdev.rhythm.domain.repository.MusicRepository
import com.longlegsdev.rhythm.util.Rhythm
import javax.inject.Inject

class GetMusicBestListUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(
        limit: Int = Rhythm.DEFAULT_LIMIT
    ) = musicRepository.getBestMusic(
        limit
    )
}