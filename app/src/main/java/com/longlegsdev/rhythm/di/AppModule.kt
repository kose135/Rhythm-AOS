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
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.upstream.DefaultBandwidthMeter
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.service.player.MusicPlayer
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
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
        val cacheDir = File(app.cacheDir, ModuleConstant.MEDIA_CACHE_DIR)
        val cacheSize = ModuleConstant.MEDIA_CACHE_SIZE

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
                .setConnectTimeoutMs(ModuleConstant.MEDIA_CONNECTION_TIMEOUT_MS)
                .setReadTimeoutMs(ModuleConstant.MEDIA_READ_TIMEOUT_MS)
                .setDefaultRequestProperties(
                    mapOf(
                        "Connection" to "keep-alive"
                    )
                )
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
    ): ExoPlayer {
        // 로드 제어 설정
        val loadControl = DefaultLoadControl.Builder()
            // 버퍼 사이즈 증가 (기본값 보다 큰 값으로 설정)
            .setBufferDurationsMs(
                DefaultLoadControl.DEFAULT_MIN_BUFFER_MS * 2,  // 최소 버퍼 크기 2배로
                DefaultLoadControl.DEFAULT_MAX_BUFFER_MS * 2,  // 최대 버퍼 크기 2배로
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_MS / 2,  // 재생을 시작하기 위한 버퍼 감소
                DefaultLoadControl.DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS / 2  // 리버퍼링 후 재생 버퍼 감소
            )
            // 버퍼링 우선순위 설정
            .setPrioritizeTimeOverSizeThresholds(true)
            .build()

        // 대역폭 측정 설정
        val bandwidthMeter = DefaultBandwidthMeter.Builder(app)
            .setResetOnNetworkTypeChange(false)  // 네트워크 변경 시에도 대역폭 측정 유지
            .setSlidingWindowMaxWeight(20)       // 대역폭 계산을 위한 샘플 수 증가
            .build()

        return ExoPlayer.Builder(app)
            .setUseLazyPreparation(false)
            .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory))
            .setLoadControl(loadControl)  // 커스텀 로드 컨트롤 적용
            .setBandwidthMeter(bandwidthMeter)  // 커스텀 대역폭 미터 적용
            // 백그라운드 작업 개선
            .setRenderersFactory(
                DefaultRenderersFactory(app)
                    .setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER)
            )
            .build()
    }
}