package com.longlegsdev.rhythm.service.player

import androidx.core.app.PendingIntentCompat.send
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.Player.RepeatMode
import androidx.media3.exoplayer.ExoPlayer
import com.longlegsdev.rhythm.service.player.PlaybackState
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.data.remote.model.Music
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicPlayer @Inject constructor(
    private val player: ExoPlayer
) : Player.Listener {

    private var currentIndex: Int = 0
    private var playlist: List<MusicEntity> = emptyList()
    private var playerState = PlaybackState.IDLE

    private var playbackStateListener: ((PlaybackState) -> Unit)? = null
    private var currentMediaItemListener: ((MediaItem?) -> Unit)? = null

    val isPlayingFlow = MutableStateFlow(false)
    val currentMusicFlow = MutableStateFlow<Music?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentPositionFlow = currentMusicFlow
        .combine(isPlayingFlow) { currentMusic, isPlaying ->
            channelFlow {
                while (isPlaying && coroutineContext.isActive) {
                    delay(250L)
                    val position = withContext(Dispatchers.Main) { player.currentPosition }
                    send(position)
                }
            }
        }.flattenMerge()
        .flowOn(Dispatchers.IO)
    //빠르게 변경되면 이전 flow가 취소되지 않은 상태에서 두 flow가 병렬로 실행될 수 있음.
    //그래서 flattenMerge로 병합함.

    val durationFlow = MutableStateFlow(0L)

    val currentMusicIndexFlow = MutableStateFlow(player.currentMediaItemIndex)
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

    fun setPlaylist(
        musics: List<MusicEntity>,
        startIndex: Int = 0
    ) {
        playlist = musics
        currentIndex = startIndex
        player.clearMediaItems()

        musics.forEach { music ->
            player.addMediaItem(MediaItem.fromUri(music.url))
        }

        player.seekTo(startIndex, 0)
        player.prepare()
    }

    fun play() {
        player.playWhenReady = true
    }

    fun play(
        musics: List<MusicEntity>,
        startIndex: Int = 0
    ) {
        playlist = musics
        currentIndex = startIndex
        player.clearMediaItems()

        musics.forEach { music ->
            player.addMediaItem(MediaItem.fromUri(music.url))
        }

        player.seekTo(startIndex, 0)
        player.prepare()

        play()
    }

    fun pause() {
        player.playWhenReady = false
    }

    fun next() {
        if (currentIndex < playlist.size - 1) {
            currentIndex++
            player.seekTo(currentIndex, 0)
            play()
        }
    }

    fun previous() {
        if (currentIndex > 0) {
            currentIndex--

            player.seekTo(currentIndex, 0)
            play()
        }
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    fun isPlaying(): Boolean = player.isPlaying

    fun getCurrentPosition(): Long = player.currentPosition

    fun getDuration(): Long = player.duration

    fun release() {
        player.release()
    }

    fun getState() = playerState

    fun getPlayer(): ExoPlayer = player

    /*
    * 재생 상태(state)가 바뀔 때 호출
    */
    override fun onPlaybackStateChanged(state: Int) {
        playerState = when (state) {
            Player.STATE_IDLE -> PlaybackState.IDLE // 준비 안 됨 (재생할 수 없음)
            Player.STATE_BUFFERING -> PlaybackState.BUFFERING // 데이터를 로드 중
            Player.STATE_READY -> { // 준비 완료, playWhenReady가 true면 바로 재생
                if (player.isPlaying) PlaybackState.PLAYING else PlaybackState.PAUSED
            }
            Player.STATE_ENDED -> PlaybackState.ENDED // 재생 종료
            else -> PlaybackState.IDLE
        }

        playbackStateListener?.invoke(playerState)
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
        currentMediaItemListener?.invoke(mediaItem)
    }

    /*
    * ExoPlayer가 실제 재생 중인지 여부가 바뀔 때 호출됨
    *
    * pause/play 상태 전환 등에서 유용
    */
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        isPlayingFlow.value = isPlaying
        durationFlow.value = player.duration
    }

    /*
    * 오류 발생 시 호출 (파일 재생 불가, 네트워크 문제 등)
    */
    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)

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