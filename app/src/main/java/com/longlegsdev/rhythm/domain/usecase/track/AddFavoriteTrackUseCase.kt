package com.longlegsdev.rhythm.domain.usecase.track

import com.longlegsdev.rhythm.data.entity.TrackEntity
import com.longlegsdev.rhythm.domain.repository.TrackRepository
import javax.inject.Inject

class AddFavoriteTrackUseCase @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend operator fun invoke(
        track: TrackEntity
    ) = trackRepository.addFavoriteTrack(
        track = track
    )
}