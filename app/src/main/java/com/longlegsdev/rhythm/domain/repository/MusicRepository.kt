package com.longlegsdev.rhythm.domain.repository

import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.MusicListEntity
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity
import com.longlegsdev.rhythm.data.remote.model.Apply
import com.longlegsdev.rhythm.domain.Result
import kotlinx.coroutines.flow.Flow

interface MusicRepository {

    suspend fun getMusicInfo(musicId: Int): Flow<Result<MusicEntity>>

    suspend fun getBestMusic(limit: Int): Flow<Result<MusicListEntity>>

    suspend fun addMusicLike(musicId: Int): Flow<Result<Apply>>

    suspend fun deleteMusicLike(musicId: Int): Flow<Result<Apply>>

    suspend fun updateRecentMusic(music: MusicEntity)

    suspend fun getRecentMusicList(): Flow<List<RecentMusicEntity>>

    suspend fun addFavoriteMusic(music: MusicEntity)

    suspend fun deleteFavoriteMusic(musicId: Int)

    suspend fun getAllFavoriteMusicList(): Flow<List<FavoriteMusicEntity>>

}