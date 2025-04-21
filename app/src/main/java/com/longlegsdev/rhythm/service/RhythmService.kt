package com.longlegsdev.rhythm.service

import android.annotation.SuppressLint
import android.app.Notification
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaLibraryService.MediaLibrarySession
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@UnstableApi
@AndroidEntryPoint
class RhythmService : MediaLibraryService(), MediaLibrarySession.Callback {

    @Inject
    lateinit var musicPlayerManager: MusicPlayerManager

    private lateinit var player: ExoPlayer
    private lateinit var mediaLibrarySession: MediaLibrarySession
    private lateinit var notificationManager: RhythmNotificationManager

    override fun onCreate() {
        super.onCreate()
        Timber.d("create rhythm service")

        player = musicPlayerManager.getPlayer() as ExoPlayer
        mediaLibrarySession = MediaLibrarySession.Builder(this, player, this)
            .setId("RHYTHM_SESSION")
            .build()

        notificationManager = RhythmNotificationManager(this, this, player, mediaLibrarySession)
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? {
        return mediaLibrarySession
    }

    override fun onGetLibraryRoot(
        session: MediaLibrarySession,
        browser: MediaSession.ControllerInfo,
        params: LibraryParams?
    ): ListenableFuture<LibraryResult<MediaItem>> {
        val rootItem = MediaItem.Builder()
            .setMediaId("root")
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Rhythm Root")
                    .setIsBrowsable(true)
                    .build()
            )
            .build()

        return Futures.immediateFuture(LibraryResult.ofItem(rootItem, params))
    }

    override fun onGetItem(
        session: MediaLibrarySession,
        browser: MediaSession.ControllerInfo,
        mediaId: String
    ): ListenableFuture<LibraryResult<MediaItem>> {
        val musicEntity = musicPlayerManager.currentMusic.value
        val item = MediaItem.Builder()
            .setMediaId(mediaId)
            .setUri(musicEntity.url)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Track $mediaId")
                    .setArtist(musicEntity.artist)
                    .setIsPlayable(true)
                    .build()
            )
            .build()

        return Futures.immediateFuture(LibraryResult.ofItem(item, null))
    }

    override fun onGetChildren(
        session: MediaLibrarySession,
        browser: MediaSession.ControllerInfo,
        parentId: String,
        page: Int,
        pageSize: Int,
        params: LibraryParams?
    ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
        val musicEntities = musicPlayerManager.musicList.value
        val items = musicEntities.map { music ->
            MediaItem.Builder()
                .setMediaId(music.id.toString())
                .setUri(music.url)
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(music.title)
                        .setArtist(music.artist)
                        .setIsPlayable(true)
                        .build()
                )
                .build()
        }

        return Futures.immediateFuture(
            LibraryResult.ofItemList(
                ImmutableList.copyOf(items),
                params
            )
        )
    }

    override fun onDestroy() {
        player.release()
        mediaLibrarySession.release()

        super.onDestroy()
    }

}
