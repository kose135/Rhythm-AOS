package com.longlegsdev.rhythm.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.longlegsdev.rhythm.data.entity.FavoriteChannelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteChannelDao {

    @Query("DELETE FROM favorite_channel WHERE id = :channelId")
    suspend fun deleteById(channelId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteChannel: FavoriteChannelEntity)

    @Query("SELECT * FROM favorite_channel ORDER BY favoriteAt DESC")
    fun getAllFavoriteChannelList(): Flow<List<FavoriteChannelEntity>>git

}