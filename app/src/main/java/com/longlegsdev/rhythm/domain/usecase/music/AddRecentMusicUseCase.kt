package com.longlegsdev.rhythm.domain.usecase.music

import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.domain.repository.MusicRepository
import javax.inject.Inject

class AddRecentMusicUseCase @Inject constructor(
    private val musicRepository: MusicRepository
) {
    suspend operator fun invoke(music: MusicEntity) = musicRepository.updateRecentMusic(music)
}