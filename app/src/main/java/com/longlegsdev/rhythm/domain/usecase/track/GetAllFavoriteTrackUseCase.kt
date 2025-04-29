package com.longlegsdev.rhythm.domain.usecase.track

import com.longlegsdev.rhythm.domain.repository.TrackRepository
import javax.inject.Inject

class GetAllFavoriteTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke() = trackRepository.getAllFavoriteTrack()
}