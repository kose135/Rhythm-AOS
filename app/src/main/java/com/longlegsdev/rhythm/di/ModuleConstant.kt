package com.longlegsdev.rhythm.di

import com.longlegsdev.rhythm.BuildConfig

// Module constants
object ModuleConstant {
    /* retrofit */
    const val BASE_URL = BuildConfig.BASE_URL
    const val CACHE_DIRECTORY = "http-cache"
    const val CACHE_SIZE = 10 * 1024 * 1024 // 10MB

    /* room */
    const val DATABASE_NAME = "rhythm.db"

    /* APP */
    /* media3 */
    const val MEDIA_CACHE_DIR = "media3_cache"
    const val MEDIA_CACHE_SIZE = 500L * 1024 * 1024 // 500MB
    const val MEDIA_CONNECTION_TIMEOUT_MS = 3000 // 3 second
    const val MEDIA_READ_TIMEOUT_MS = 3000 // 3 second
}