package com.longlegsdev.rhythm.domain.usecase.track

import com.longlegsdev.rhythm.domain.repository.TrackRepository
import javax.inject.Inject

class DeleteFavoriteTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(
        trackId: Int
    ) = trackRepository.deleteFavoriteMusic(
        trackId = trackId
    )
}