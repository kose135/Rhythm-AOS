package com.longlegsdev.rhythm.service.player

import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
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

    private var playerState = PlaybackState.IDLE

    private var onPlaybackStateChanged: ((PlaybackState) -> Unit)? = null
    private var onIsPlayingChanged: ((Boolean) -> Unit)? = null
    private var onMediaItemChanged: (() -> Unit)? = null

//    val musicCountFlow = MutableStateFlow(0)
//    val repeatModeFlow = MutableStateFlow(RepeatMode.NONE)
//    val isShuffleFlow = MutableStateFlow(false)

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

    fun pause() {
        player.playWhenReady = false
    }

    fun next() {
        if (player.hasNextMediaItem()) {
            player.seekToNext()
        }
    }

    fun previous() {
        if (player.hasPreviousMediaItem()) {
            player.seekToPrevious()
        }
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

    fun getState() = playerState

    fun getPlayer(): ExoPlayer = player

    fun setOnPlaybackStateChangedListener(listener: (PlaybackState) -> Unit) {
        onPlaybackStateChanged = listener
    }

    fun setOnIsPlayingChangedListener(listener: (Boolean) -> Unit) {
        onIsPlayingChanged = listener
    }

    fun setOnMediaItemChangedListener(listener: () -> Unit) {
        onMediaItemChanged = listener
    }

    /*
    * 재생 상태(state)가 바뀔 때 호출
    */
    override fun onPlaybackStateChanged(state: Int) {
        playerState = when (state) {
            Player.STATE_IDLE -> PlaybackState.IDLE
            Player.STATE_BUFFERING -> PlaybackState.BUFFERING
            Player.STATE_READY -> {
                if (player.isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED
            }

            Player.STATE_ENDED -> PlaybackState.ENDED
            else -> PlaybackState.IDLE
        }

        onPlaybackStateChanged?.invoke(playerState)
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
        onMediaItemChanged?.invoke()
    }

    /*
    * ExoPlayer가 실제 재생 중인지 여부가 바뀔 때 호출됨
    * pause/play 상태 전환 등에서 유용
    */
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        onIsPlayingChanged?.invoke(isPlaying)
    }

    /*
    * 오류 발생 시 호출 (파일 재생 불가, 네트워크 문제 등)
    */
    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)

        val cause = error.cause
        if (cause is HttpDataSourceException) {
            // An HTTP error occurred.
            val httpError = cause
            // It's possible to find out more about the error both by casting and by querying
            // the cause.
            if (httpError is InvalidResponseCodeException) {
                // Cast to InvalidResponseCodeException and retrieve the response code, message
                // and headers.
            } else {
                // Try calling httpError.getCause() to retrieve the underlying cause, although
                // note that it may be null.
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