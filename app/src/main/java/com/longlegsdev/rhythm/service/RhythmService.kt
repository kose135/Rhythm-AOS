package com.longlegsdev.rhythm.service

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaLibraryService.MediaLibrarySession
import androidx.media3.session.MediaSession
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.longlegsdev.rhythm.domain.usecase.music.MusicUseCase
import com.longlegsdev.rhythm.service.player.MusicPlayerManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RhythmService() : MediaLibraryService(), MediaLibrarySession.Callback {

    @Inject
    lateinit var musicPlayerManager: MusicPlayerManager

    @Inject
    lateinit var musicUseCase: MusicUseCase

    private var player: ExoPlayer? = null
    private var mediaLibrarySession: MediaLibrarySession? = null

    init {
        player = musicPlayerManager.getPlayer()
        mediaLibrarySession = MediaLibrarySession.Builder(this, player!!, this)
            .setId("RHYTHM_SESSION")
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaLibrarySession? {
        return mediaLibrarySession
    }

    override fun onDestroy() {
        mediaLibrarySession?.run {
            player.release()
            release()
            mediaLibrarySession = null
        }
        super.onDestroy()
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
        val item = MediaItem.Builder()
            .setMediaId(mediaId)
            .setUri("https://example.com/audio/$mediaId.mp3")
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle("Track $mediaId")
                    .setArtist("Artist")
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
        val items = List(10) { index ->
            MediaItem.Builder()
                .setMediaId("track-$index")
                .setUri("https://example.com/audio/track-$index.mp3")
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle("Track $index")
                        .setIsPlayable(true)
                        .build()
                )
                .build()
        }

        return Futures.immediateFuture(LibraryResult.ofItemList(items, params))
    }
}

