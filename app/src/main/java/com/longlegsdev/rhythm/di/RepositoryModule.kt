package com.longlegsdev.rhythm.di

import com.longlegsdev.rhythm.data.repository.TrackRepositoryImpl
import com.longlegsdev.rhythm.data.repository.MusicRepositoryImpl
import com.longlegsdev.rhythm.domain.repository.TrackRepository
import com.longlegsdev.rhythm.domain.repository.MusicRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindTrackRepository(
        trackRepositoryImpl: TrackRepositoryImpl
    ): TrackRepository

    @Binds
    fun bindMusicRepository(
        musicRepositoryImpl: MusicRepositoryImpl
    ): MusicRepository

}