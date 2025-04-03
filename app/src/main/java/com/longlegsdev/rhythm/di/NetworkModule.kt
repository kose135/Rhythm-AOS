package com.longlegsdev.rhythm.di

import android.content.Context
import com.longlegsdev.rhythm.data.remote.RhythmApiService
import com.longlegsdev.rhythm.data.remote.interceptor.AuthInterceptor
import com.longlegsdev.rhythm.util.Rhythm
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            // Level.NONE: 로그 출력하지 않음
            // Level.BASIC: 요청 및 응답의 기본 정보만 로그로 출력
            // Level.HEADERS: 요청 및 응답의 헤더 정보만 로그로 출력
            // Level.BODY: 요청 및 응답의 모든 내용을 로그로 출력
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideCache(
        @ApplicationContext app: Context,
    ): Cache {
        val cacheDirectory = File(app.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024 // 10MB
        return Cache(cacheDirectory, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
        @ApplicationContext app: Context,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthInterceptor(app))
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): RhythmApiService = Retrofit.Builder()
        .run {
            baseUrl(Rhythm.BASE_URL)
            client(okHttpClient)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            build()
        }.create(RhythmApiService::class.java)
}
