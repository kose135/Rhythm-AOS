package com.longlegsdev.rhythm.domain.usecase.track

import com.longlegsdev.rhythm.domain.repository.TrackRepository
import com.longlegsdev.rhythm.util.Rhythm
import javax.inject.Inject

class GetTrackListUseCase @Inject constructor(
    private val trackRepository: TrackRepository
){
    suspend operator fun invoke(
        page: Int = Rhythm.DEFAULT_PAGE,
        offset: Int = Rhythm.DEFAULT_OFFSET
    ) = trackRepository.getTracks(
        page = page,
        offset = offset
    )
}