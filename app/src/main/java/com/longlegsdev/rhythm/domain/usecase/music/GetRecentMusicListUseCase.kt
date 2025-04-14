package com.longlegsdev.rhythm.domain.usecase.music

import com.longlegsdev.rhythm.domain.repository.MusicRepository
import com.longlegsdev.rhythm.util.Rhythm
import javax.inject.Inject

class GetRecentMusicListUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke() = musicRepository.getRecentMusicList()
}