package com.longlegsdev.rhythm.data.repository

import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.mapper.asEntity
import com.longlegsdev.rhythm.data.remote.RhythmApiService
import com.longlegsdev.rhythm.data.remote.model.Apply
import com.longlegsdev.rhythm.data.remote.model.MusicInfo
import com.longlegsdev.rhythm.data.remote.model.MusicList
import com.longlegsdev.rhythm.domain.Result
import com.longlegsdev.rhythm.domain.mapper
import com.longlegsdev.rhythm.domain.repository.MusicRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MusicRepositoryImpl @Inject constructor(
    private val service: RhythmApiService
) : BaseRepository(), MusicRepository {

    override suspend fun getMusicInfo(
        musicId: Int
    ): Flow<Result<MusicEntity>> {
        return safeApiCall { service.getMusicInfo(musicId) }
            .mapper { response: MusicInfo ->
                response.info.asEntity()
            }
    }

    override suspend fun getBestMusic(
        limit: Int
    ): Flow<Result<MusicListEntity>> {
        return safeApiCall { service.getMusicBest(limit) }
            .mapper { response: MusicList ->
                response.asEntity()
            }
    }

    override suspend fun addMusicLike(
        musicId: Int
    ): Flow<Result<Apply>> {
        return safeApiCall { service.addMusicLike(musicId) }
    }

    override suspend fun deleteMusicLike(
        musicId: Int
    ): Flow<Result<Apply>> {
        return safeApiCall { service.deleteMusicLike(musicId) }
    }

}