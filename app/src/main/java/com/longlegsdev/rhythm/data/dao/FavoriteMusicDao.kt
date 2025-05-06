package com.longlegsdev.rhythm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMusicDao {

    @Query("DELETE FROM favorite_music WHERE id = :musicId")
    suspend fun deleteById(musicId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteMusic: FavoriteMusicEntity)

    @Query("SELECT * FROM favorite_music ORDER BY favoritedAt DESC")
    fun getAllFavoriteMusicList(): Flow<List<FavoriteMusicEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_music WHERE id = :musicId)")
    fun isFavorite(musicId: Int): Flow<Boolean>

}
