package com.longlegsdev.rhythm.domain.repository

import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface MusicRepository {

    suspend fun getMusicInfo(musicId: Int): Flow<Result<MusicEntity>>

    suspend fun getBestMusic(limit: Int): Flow<Result<MusicListEntity>>

    suspend fun updateRecentMusic(music: MusicEntity)

    suspend fun getRecentMusicList(): Flow<Result<List<MusicEntity>>>

    suspend fun addFavoriteMusic(musicId: Int)

    suspend fun deleteFavoriteMusic(musicId: Int)

    suspend fun getAllFavoriteMusicList(): Flow<Result<List<MusicEntity>>>

    suspend fun isFavoritedMusic(musicId: Int): Flow<Boolean>

}