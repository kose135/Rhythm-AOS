package com.longlegsdev.rhythm.di

import com.longlegsdev.rhythm.domain.repository.ChannelRepository
import com.longlegsdev.rhythm.domain.repository.MusicRepository
import com.longlegsdev.rhythm.domain.usecase.channel.AddChannelLikeUseCase
import com.longlegsdev.rhythm.domain.usecase.channel.AddFavoriteChannelUseCase
import com.longlegsdev.rhythm.domain.usecase.channel.ChannelUseCase
import com.longlegsdev.rhythm.domain.usecase.channel.DeleteChannelLikeUseCase
import com.longlegsdev.rhythm.domain.usecase.channel.DeleteFavoriteChannelUseCase
import com.longlegsdev.rhythm.domain.usecase.channel.GetAllFavoriteChannelUseCase
import com.longlegsdev.rhythm.domain.usecase.channel.GetChannelListUseCase
import com.longlegsdev.rhythm.domain.usecase.channel.GetChannelMusicListUseCase
import com.longlegsdev.rhythm.domain.usecase.channel.GetChannelRecommendedUseCase
import com.longlegsdev.rhythm.domain.usecase.music.AddFavoriteMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.AddMusicLikeUseCase
import com.longlegsdev.rhythm.domain.usecase.music.AddRecentMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.DeleteFavoriteMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.DeleteMusicLikeUseCase
import com.longlegsdev.rhythm.domain.usecase.music.GetAllFavoriteMusicUseCase
import com.longlegsdev.rhythm.domain.usecase.music.GetMusicBestListUseCase
import com.longlegsdev.rhythm.domain.usecase.music.GetMusicInfoUseCase
import com.longlegsdev.rhythm.domain.usecase.music.GetRecentMusicListUseCase
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
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
    fun provideChannelUseCase(repository: ChannelRepository): ChannelUseCase {
        return ChannelUseCase(
            getList = GetChannelListUseCase(repository),
            getRecommendedList = GetChannelRecommendedUseCase(repository),
            getMusicList = GetChannelMusicListUseCase(repository),
            addLike = AddChannelLikeUseCase(repository),
            delLike = DeleteChannelLikeUseCase(repository),
            addFavorite = AddFavoriteChannelUseCase(repository),
            delFavorite = DeleteFavoriteChannelUseCase(repository),
            getAllFavorite = GetAllFavoriteChannelUseCase(repository),
        )
    }

    @Provides
    @Singleton
    fun provideMusicUseCase(repository: MusicRepository): MusicUseCase {
        return MusicUseCase(
            getInfo = GetMusicInfoUseCase(repository),
            getBestList = GetMusicBestListUseCase(repository),
            addLike = AddMusicLikeUseCase(repository),
            delLike = DeleteMusicLikeUseCase(repository),
            getRecentList = GetRecentMusicListUseCase(repository),
            addRecent = AddRecentMusicUseCase(repository),
            addFavorite = AddFavoriteMusicUseCase(repository),
            delFavorite = DeleteFavoriteMusicUseCase(repository),
            getAllFavorite = GetAllFavoriteMusicUseCase(repository),
        )
    }

}