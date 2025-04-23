package com.longlegsdev.rhythm.util

import com.longlegsdev.rhythm.BuildConfig

object Rhythm {

    /* Notification values */
    const val NOTIFICATION_ID = 100001
    const val CHANNEL_ID = "rhythm_channel_id"
    const val CHANNEL_NAME = "rhythm_channel_name"
    const val CHANNEL_DESCRIPTION = "Controls for music playback"


    /* DI values */
    const val BASE_URL = BuildConfig.BASE_URL
    const val DATABASE_NAME = "rhythm.db"
    const val MEDIA_CACHE_DIR = "media3_cache"
    const val MEDIA_CACHE_SIZE = 500L * 1024 * 1024 // 500MB

    /* Screen values */
    const val SPLASH_DURATION = 2000


    /* Default API values */
    const val DEFAULT_PAGE = 1
    const val DEFAULT_OFFSET = 10
    const val DEFAULT_LIMIT = 10


}