package com.longlegsdev.rhythm.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.service.player.MusicPlayer
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
import com.longlegsdev.rhythm.util.Rhythm
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
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
    fun provideMusicPlayer(
        exoPlayer: ExoPlayer
    ): MusicPlayer {
        return MusicPlayer(exoPlayer)
    }

    @Provides
    @Singleton
    fun provideMusicPlayerManager(
        @ApplicationContext app: Context,
        musicPlayer: MusicPlayer,
        musicUseCase: MusicUseCase
    ): MusicPlayerManager {
        return MusicPlayerManager(app, musicPlayer, musicUseCase)
    }

    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun providesCacheDataSourceFactory(
        @ApplicationContext app: Context
    ): CacheDataSource.Factory {
        // set cache directory and size
        val cacheDir = File(app.cacheDir, Rhythm.MEDIA_CACHE_DIR)
        val cacheSize = Rhythm.MEDIA_CACHE_SIZE

        // create cache database
        val simpleCache = SimpleCache(
            cacheDir,
            LeastRecentlyUsedCacheEvictor(cacheSize),
            StandaloneDatabaseProvider(app)
        )

        // create a basic datasource factory
        val upstreamFactory = DefaultDataSource.Factory(
            app,
            DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true)
                .setConnectTimeoutMs(150)
                .setReadTimeoutMs(150)
        )

        // create and return a cache data source factory
        return CacheDataSource.Factory()
            .setCache(simpleCache)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setCacheWriteDataSinkFactory(null)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }

    @OptIn(UnstableApi::class)
    @Provides
    @Singleton
    fun providesExoplayer(
        @ApplicationContext app: Context,
        cacheDataSourceFactory: CacheDataSource.Factory
    ): ExoPlayer = ExoPlayer
        .Builder(app)
        .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
        .build()
}