package com.longlegsdev.rhythm.domain.usecase.track

import com.longlegsdev.rhythm.domain.repository.TrackRepository
import javax.inject.Inject

class GetRecommendTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(
        limit: Int = 10
    ) = trackRepository.getRecommendedTracks(
        limit = limit
    )
}