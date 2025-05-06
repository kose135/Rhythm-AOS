package com.longlegsdev.rhythm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.longlegsdev.rhythm.data.entity.FavoriteTrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTrackDao {

    @Query("DELETE FROM favorite_track WHERE id = :trackId")
    suspend fun deleteById(trackId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteTrackEntity: FavoriteTrackEntity)

    @Query("SELECT * FROM favorite_track ORDER BY favoritedAt DESC")
    fun getAllFavoriteTrackList(): Flow<List<FavoriteTrackEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_track WHERE id = :trackId)")
    fun isFavorite(trackId: Int): Flow<Boolean>

}