package com.longlegsdev.rhythm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.longlegsdev.rhythm.data.entity.FavoriteTrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTrackDao {

    @Query("DELETE FROM favorite_track WHERE id = :channelId")
    suspend fun deleteById(channelId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteChannel: FavoriteTrackEntity)

    @Query("SELECT * FROM favorite_track ORDER BY favoritedAt DESC")
    fun getAllFavoriteTrackList(): Flow<List<FavoriteTrackEntity>>

}