package com.longlegsdev.rhythm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentMusicDao {
    @Query("DELETE FROM recent_music WHERE id = :musicId")
    suspend fun deleteById(musicId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(music: RecentMusicEntity)

    @Query("SELECT * FROM recent_music ORDER BY playedAt DESC")
    fun getAllRecentMusicList(): Flow<List<RecentMusicEntity>>

    // 최대 50개까지 저장
    @Query("DELETE FROM recent_music WHERE id NOT IN (SELECT id FROM recent_music ORDER BY playedAt DESC LIMIT 50)")
    suspend fun trimToRecentLimit()
}