package com.longlegsdev.rhythm.di

import com.longlegsdev.rhythm.domain.repository.TrackRepository
import com.longlegsdev.rhythm.domain.repository.MusicRepository
import com.longlegsdev.rhythm.domain.usecase.track.AddFavoriteTrackUseCase
import com.longlegsdev.rhythm.domain.usecase.track.TrackUseCase
import com.longlegsdev.rhythm.domain.usecase.track.DeleteFavoriteTrackUseCase
import com.longlegsdev.rhythm.domain.usecase.track.GetAllFavoriteTrackUseCase
import com.longlegsdev.rhythm.domain.usecase.track.GetTrackListUseCase
import com.longlegsdev.rhythm.domain.usecase.track.GetRecommendTrackUseCase
import com.longlegsdev.rhythm.domain.usecase.music.AddFavoriteMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.AddRecentMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.DeleteFavoriteMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.GetAllFavoriteMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.GetMusicBestListUseCase
import com.longlegsdev.rhythm.domain.usecase.music.GetMusicInfoUseCase
import com.longlegsdev.rhythm.domain.usecase.music.GetRecentMusicListUseCase
import com.longlegsdev.rhythm.domain.usecase.music.IsFavoriteMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.domain.usecase.track.GetTrackMusicListUseCase
import com.longlegsdev.rhythm.domain.usecase.track.IsFavoriteTrackUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent::class
)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideTrackUseCase(repository: TrackRepository): TrackUseCase {
        return TrackUseCase(
            getList = GetTrackListUseCase(repository),
            getRecommendedList = GetRecommendTrackUseCase(repository),
            getMusicList = GetTrackMusicListUseCase(repository),
            addFavorite = AddFavoriteTrackUseCase(repository),
            delFavorite = DeleteFavoriteTrackUseCase(repository),
            getAllFavorite = GetAllFavoriteTrackUseCase(repository),
            isFavoritedTrack = IsFavoriteTrackUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideMusicUseCase(repository: MusicRepository): MusicUseCase {
        return MusicUseCase(
            getInfo = GetMusicInfoUseCase(repository),
            getBestList = GetMusicBestListUseCase(repository),
            getRecentList = GetRecentMusicListUseCase(repository),
            addRecent = AddRecentMusicUseCase(repository),
            addFavorite = AddFavoriteMusicUseCase(repository),
            delFavorite = DeleteFavoriteMusicUseCase(repository),
            getAllFavorite = GetAllFavoriteMusicUseCase(repository),
            isFavoritedMusic = IsFavoriteMusicUseCase(repository),
        )
    }

}