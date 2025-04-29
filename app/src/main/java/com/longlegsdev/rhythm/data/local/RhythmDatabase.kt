package com.longlegsdev.rhythm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.longlegsdev.rhythm.data.dao.FavoriteTrackDao
import com.longlegsdev.rhythm.data.dao.FavoriteMusicDao
import com.longlegsdev.rhythm.data.dao.RecentMusicDao
import com.longlegsdev.rhythm.data.entity.TrackEntity
import com.longlegsdev.rhythm.data.entity.FavoriteTrackEntity
import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity

@Database(
    entities = [
        MusicEntity::class,
        RecentMusicEntity::class,
        TrackEntity::class,
        FavoriteMusicEntity::class,
        FavoriteTrackEntity::class,
    ],
    version = 1,
)
abstract class RhythmDatabase : RoomDatabase() {
    abstract val recentMusicDao: RecentMusicDao
    abstract val favoriteMusicDao: FavoriteMusicDao
    abstract val favoriteTrackDao: FavoriteTrackDao
}