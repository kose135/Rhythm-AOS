package com.longlegsdev.rhythm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.longlegsdev.rhythm.data.dao.FavoriteChannelDao
import com.longlegsdev.rhythm.data.dao.FavoriteMusicDao
import com.longlegsdev.rhythm.data.dao.RecentMusicDao
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.data.entity.FavoriteChannelEntity
import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity

@Database(
    entities = [
        MusicEntity::class,
        RecentMusicEntity::class,
        ChannelEntity::class,
        FavoriteMusicEntity::class,
        FavoriteChannelEntity::class,
    ],
    version = 1,
)
abstract class RhythmDatabase : RoomDatabase() {
    abstract val recentMusicDao: RecentMusicDao
    abstract val favoriteMusicDao: FavoriteMusicDao
    abstract val favoriteChannelDao: FavoriteChannelDao
}