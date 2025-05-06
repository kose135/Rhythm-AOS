package com.longlegsdev.rhythm.domain.repository

import kotlin.Int
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.entity.TrackEntity
import com.longlegsdev.rhythm.data.entity.TrackListEntity
import com.longlegsdev.rhythm.domain.Result
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

    suspend fun getTracks(page: Int, offset: Int): Flow<Result<TrackListEntity>>

    suspend fun getRecommendedTracks(limit: Int): Flow<Result<TrackListEntity>>

    suspend fun getTrackMusic(trackId: Int): Flow<Result<MusicListEntity>>

    suspend fun addFavoriteTrack(trackId: Int)

    suspend fun deleteFavoriteMusic(trackId: Int)

    suspend fun getAllFavoriteTrack(): Flow<Result<List<TrackEntity>>>

    suspend fun isFavoritedTrack(trackId: Int): Flow<Boolean>
}