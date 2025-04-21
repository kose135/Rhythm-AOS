package com.longlegsdev.rhythm.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.longlegsdev.rhythm.service.player.MusicPlayer
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi = Moshi
        .Builder()
        .run {
            add(KotlinJsonAdapterFactory())
            build()
        }

    @Provides
    @Singleton
    fun providesExoplayer(
        @ApplicationContext app: Context
    ): ExoPlayer = ExoPlayer
        .Builder(app)
        .build()

    @Provides
    @Singleton
    fun provideMusicPlayer(
        exoPlayer: ExoPlayer
    ): MusicPlayer {
        return MusicPlayer(exoPlayer)
    }

    @Provides
    @Singleton
    fun provideMusicPlayerManager(
        @ApplicationContext app: Context,
        musicPlayer: MusicPlayer
    ): MusicPlayerManager {
        return MusicPlayerManager(app, musicPlayer)
    }

}