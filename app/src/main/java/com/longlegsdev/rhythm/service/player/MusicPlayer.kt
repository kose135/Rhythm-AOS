package com.longlegsdev.rhythm.service.player

import androidx.annotation.OptIn
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.HttpDataSource.HttpDataSourceException
import androidx.media3.datasource.HttpDataSource.InvalidResponseCodeException
import androidx.media3.exoplayer.ExoPlayer
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.remote.model.Music
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayer @Inject constructor(
    private val player: ExoPlayer
) : Player.Listener {
    private var playerEventListener: MusicPlayerEventListener? = null

    init {
        Timber.d("Music Player init")
        setup()
    }

    private fun setup() {
        player.addListener(this)
    }

    fun setMediaItems(
        musicList: List<MusicEntity>,
        startIndex: Int = 0
    ) {
        player.clearMediaItems()

        val items = musicList.map { music ->
            MediaItem.Builder()
                .setUri(music.url)
                .setMediaId(music.id.toString())
                .setMediaMetadata(
                    MediaMetadata.Builder()
                        .setTitle(music.title)
                        .setArtist(music.artist)
                        .build()
                )
                .build()
        }

        player.setMediaItems(items, startIndex, 0L)
    }

    fun prepare() {
        player.prepare()
    }

    fun play() {
        prepare()
        player.playWhenReady = true
    }

    fun play(index: Int) {
        player.seekTo(index, C.TIME_UNSET)
        prepare()
        player.playWhenReady = true
    }

    fun pause() {
        player.playWhenReady = false
    }

    fun hasNext(): Boolean = player.hasNextMediaItem()

    fun next() {
        player.seekToNext()
    }

    fun hasPrevious(): Boolean = player.hasPreviousMediaItem()

    fun previous() {
        player.seekToPrevious()
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    fun getCurrentIndex(): Int = player.currentMediaItemIndex

    fun getCurrentPosition(): Long = player.currentPosition

    fun getDuration(): Long = player.duration

    fun getBuffer(): Long = player.bufferedPosition

    fun release() {
        player.release()
    }

    fun getPlayer(): ExoPlayer = player

    fun setMusicPlayerEventListener(listener: MusicPlayerEventListener) {
        playerEventListener = listener
    }

    /*
    * 재생 상태(state)가 바뀔 때 호출
    */
    override fun onPlaybackStateChanged(state: Int) {
        Timber.d("Playback state changed: $state")

        val playerState = when (state) {
            Player.STATE_IDLE -> PlaybackState.IDLE
            Player.STATE_BUFFERING -> PlaybackState.BUFFERING
            Player.STATE_READY -> {
                if (player.isPlaying)
                    PlaybackState.PLAYING
                else
                    PlaybackState.PAUSED
            }
            Player.STATE_ENDED -> PlaybackState.ENDED
            else -> PlaybackState.IDLE
        }

        playerEventListener?.onPlaybackStateChanged(playerState)
    }

    /*
    * 다음 트랙으로 넘어가거나, 플레이리스트 항목이 바뀔 때 호출됨
    *
    * MEDIA_ITEM_TRANSITION_REASON_AUTO: 자동 전환 (예: 다음 트랙)
    * MEDIA_ITEM_TRANSITION_REASON_SEEK: 사용자가 seekTo로 전환
    * MEDIA_ITEM_TRANSITION_REASON_PLAYLIST_CHANGED: 플레이리스트 변경
    * MEDIA_ITEM_TRANSITION_REASON_REPEAT: 반복 재생
    */
    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        Timber.d("Media item changed: $mediaItem")
        playerEventListener?.onMediaItemChanged(mediaItem)
    }

    /*
    * ExoPlayer가 실제 재생 중인지 여부가 바뀔 때 호출됨
    * pause/play 상태 전환 등에서 유용
    */
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        Timber.d("Is playing: $isPlaying")
        playerEventListener?.onIsPlayingChanged(isPlaying)
    }

    /*
    * 오류 발생 시 호출 (파일 재생 불가, 네트워크 문제 등)
    */
    @OptIn(UnstableApi::class)
    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)

        // 외부 리스너에 위임
        playerEventListener?.onPlayerError(error)

        // 기본 오류 로그 출력
        Timber.e(error, "Playback error occurred: ${error.message}")
        Timber.e("Stack trace:\n${error.stackTraceToString()}")

        // An HTTP error occurred.
        when (val cause = error.cause) {
            // It's possible to find out more about the error both by casting and by querying
            // the cause.
            is InvalidResponseCodeException -> {
                Timber.e("InvalidResponseCodeException: ${cause.responseCode}")
                Timber.e("Response headers: ${cause.headerFields}")
            }
            // Cast to InvalidResponseCodeException and retrieve the response code, message
            // and headers.
            is HttpDataSourceException -> {
                Timber.e("HttpDataSourceException occurred: ${cause.message} | Cause: ${cause.cause}")
            }
            // Try calling httpError.getCause() to retrieve the underlying cause, although
            // note that it may be null.
            else -> {
                Timber.e("Unknown playback error cause: ${cause?.message ?: "No additional info"}")
            }
        }
    }

    /*
    * playWhenReady가 변경될 때 호출
    *
    * PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST: 유저가 수동으로 변경
    * PLAY_WHEN_READY_CHANGE_REASON_AUDIO_FOCUS_LOSS: 오디오 포커스 잃음 등
    */
    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)


    }

    // 반복 모드가 바뀔 때
//    override fun onRepeatModeChanged(repeatMode: Int) {
//        super.onRepeatModeChanged(repeatMode)
//
//    }

    // 셔플 설정 변경 시
//    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
//        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
//
//    }


}