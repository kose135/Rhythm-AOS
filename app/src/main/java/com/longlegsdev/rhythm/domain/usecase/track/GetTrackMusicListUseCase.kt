package com.longlegsdev.rhythm.domain.usecase.track

import com.longlegsdev.rhythm.domain.repository.TrackRepository
import javax.inject.Inject

class GetTrackMusicListUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(
        trackId: Int
    ) = trackRepository.getTrackMusic(
        trackId = trackId
    )
}