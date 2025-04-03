package com.longlegsdev.rhythm.domain.repository

interface MusicRepository {

    suspend fun getMusicInfo(musicId: Int)

    suspend fun getBestMusic()

    suspend fun addMusicLike(musicId: Int)

    suspend fun removeMusicLike(musicId: Int)

}