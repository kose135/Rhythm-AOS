package com.longlegsdev.rhythm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.longlegsdev.rhythm.data.entity.FavoriteChannelEntity
import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMusicDao {

    @Query("DELETE FROM favorite_music WHERE id = :channelId")
    suspend fun deleteById(channelId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteMusic: FavoriteMusicEntity)

    @Query("SELECT * FROM favorite_music ORDER BY favoriteAt DESC")
    fun getAllFavoriteMusicList(): Flow<List<FavoriteMusicEntity>>

}