package com.longlegsdev.rhythm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.longlegsdev.rhythm.data.entity.FavoriteChannelEntity

@Dao
interface FavoriteChannelDao {

    @Query("DELETE FROM favorite_channel WHERE id = :channelId")
    suspend fun deleteById(channelId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteChannel: FavoriteChannelEntity)

    @Query("SELECT * FROM favorite_channel ORDER BY favoriteAt DESC")
    suspend fun getAllFavoriteChannelList(): List<FavoriteChannelEntity>

}