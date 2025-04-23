package com.longlegsdev.rhythm

import android.annotation.SuppressLint
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.longlegsdev.rhythm.util.Rhythm
import com.longlegsdev.rhythm.util.RhythmPrefs
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RhythmApplication : Application() {

    companion object {
        private lateinit var instance: RhythmApplication

        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        setDeviceId()

        Timber.plant(Timber.DebugTree())
    }

    @SuppressLint("HardwareIds")
    fun setDeviceId() {
        val id =
            Settings.Secure.getString(getAppContext().contentResolver, Settings.Secure.ANDROID_ID)
        RhythmPrefs.deviceId = id
    }

}