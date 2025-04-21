package com.longlegsdev.rhythm.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import androidx.media3.ui.PlayerNotificationManager.MediaDescriptionAdapter
import com.longlegsdev.rhythm.util.Rhythm
import timber.log.Timber

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class RhythmNotificationManager(
    private val context: Context,
    private val service: RhythmService,
    private val player: ExoPlayer,
    private val mediaSession: MediaSession,
) : MediaDescriptionAdapter, PlayerNotificationManager.NotificationListener {

    private var playerNotificationManager: PlayerNotificationManager? = null

    private val notificationId = Rhythm.NOTIFICATION_ID
    private val channelId = Rhythm.CHANNEL_ID
    private val channelName = Rhythm.CHANNEL_NAME
    private val channelDescription = Rhythm.CHANNEL_DESCRIPTION

    init {
        Timber.d("init rhythm notification")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Timber.d("create notification channel")
            Timber.d("current version: ${Build.VERSION.SDK_INT}")
            createNotificationChannel()
        }
        setupNotificationManager()
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
            setShowBadge(false)
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }

    private fun setupNotificationManager() {
        playerNotificationManager =
            PlayerNotificationManager.Builder(context, notificationId, channelId)
                .setMediaDescriptionAdapter(this)
                .setNotificationListener(this)
                .setChannelImportance(NotificationManager.IMPORTANCE_LOW)
                .setSmallIconResourceId(android.R.drawable.ic_media_play)
                .setPlayActionIconResourceId(android.R.drawable.ic_media_play)
                .setPauseActionIconResourceId(android.R.drawable.ic_media_pause)
                .setStopActionIconResourceId(android.R.drawable.ic_media_ff)
                .setRewindActionIconResourceId(android.R.drawable.ic_media_previous)
                .setFastForwardActionIconResourceId(android.R.drawable.ic_media_next)
                .setPreviousActionIconResourceId(android.R.drawable.ic_media_rew)
                .setNextActionIconResourceId(android.R.drawable.ic_media_ff)
                .build()

        // 사용할 컨트롤 버튼 설정
        playerNotificationManager?.setUsePlayPauseActions(true)
        playerNotificationManager?.setUseNextAction(true)
        playerNotificationManager?.setUsePreviousAction(true)
        playerNotificationManager?.setUseRewindAction(false)
        playerNotificationManager?.setUseFastForwardAction(false)
        playerNotificationManager?.setUseStopAction(false)

        // seekBar는 알림에서 직접 제공하지 않음 (플레이어 UI에서 구현)

        // MediaSession 연결
        mediaSession.let {
            playerNotificationManager?.setMediaSessionToken(it.platformToken)
        }

        playerNotificationManager?.setPlayer(player)
    }

    // MediaDescriptionAdapter
    override fun getCurrentContentTitle(player: androidx.media3.common.Player): CharSequence {
        return player.currentMediaItem?.mediaMetadata?.title ?: "Unknown Title"
    }

    override fun createCurrentContentIntent(player: androidx.media3.common.Player): PendingIntent? =
        null

    override fun getCurrentContentText(player: androidx.media3.common.Player): CharSequence? {
        return player.currentMediaItem?.mediaMetadata?.artist ?: "Unknown Artist"
    }

    override fun getCurrentLargeIcon(
        player: androidx.media3.common.Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        // 앨범 아트 로직 - 여기서는 null 반환
        // 실제로는 앨범 아트를 로드하는 로직을 구현
        return null
    }

    // PlayerNotificationManager.NotificationListener
    @SuppressLint("UnsafeOptInUsageError")
    override fun onNotificationCancelled(notificationId: Int, dismissedByUser: Boolean) {
        service.stopSelf()
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun onNotificationPosted(
        notificationId: Int,
        notification: Notification,
        ongoing: Boolean
    ) {
        if (ongoing) {
            service.startForeground(notificationId, notification)
        } else {
            service.stopForeground(false)
        }
    }
}