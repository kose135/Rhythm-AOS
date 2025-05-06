package com.longlegsdev.rhythm.domain.usecase.track

import com.longlegsdev.rhythm.domain.repository.TrackRepository
import javax.inject.Inject

class IsFavoriteTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(
        trackId: Int
    ) = trackRepository.isFavoritedTrack(
        trackId = trackId
    )
}