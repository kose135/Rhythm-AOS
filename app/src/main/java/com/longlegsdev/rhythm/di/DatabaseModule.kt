package com.longlegsdev.rhythm.di

import android.content.Context
import androidx.room.Room
import com.longlegsdev.rhythm.data.dao.FavoriteTrackDao
import com.longlegsdev.rhythm.data.dao.FavoriteMusicDao
import com.longlegsdev.rhythm.data.dao.RecentMusicDao
import com.longlegsdev.rhythm.data.local.RhythmDatabase
import com.longlegsdev.rhythm.util.Rhythm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext app: Context,
    ): RhythmDatabase {
        return Room.databaseBuilder(
            app,
            RhythmDatabase::class.java,
            ModuleConstant.DATABASE_NAME
        ).run {
            build()
        }
    }

    @Provides
    fun provideRecentMusicDao(database: RhythmDatabase): RecentMusicDao {
        return database.recentMusicDao
    }

    @Provides
    fun provideFavoriteMusicDao(database: RhythmDatabase): FavoriteMusicDao {
        return database.favoriteMusicDao
    }

    @Provides
    fun provideFavoriteChannelDao(database: RhythmDatabase): FavoriteTrackDao {
        return database.favoriteTrackDao
    }

}