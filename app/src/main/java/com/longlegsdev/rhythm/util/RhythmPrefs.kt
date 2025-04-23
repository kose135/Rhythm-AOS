package com.longlegsdev.rhythm.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.longlegsdev.rhythm.RhythmApplication

object RhythmPrefs {
    private const val PREF_NAME = "rhythm_preferences"

    private val prefs: SharedPreferences by lazy {
        RhythmApplication.getAppContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setString(key: String, value: String) = prefs.edit().putString(key, value).apply()
    fun getString(key: String, default: String = "") = prefs.getString(key, default) ?: default

    fun setInt(key: String, value: Int) = prefs.edit().putInt(key, value).apply()
    fun getInt(key: String, default: Int = 0) = prefs.getInt(key, default)

    fun setBoolean(key: String, value: Boolean) = prefs.edit().putBoolean(key, value).apply()
    fun getBoolean(key: String, default: Boolean = false) = prefs.getBoolean(key, default)

    fun setLong(key: String, value: Long) = prefs.edit().putLong(key, value).apply()
    fun getLong(key: String, default: Long = 0L) = prefs.getLong(key, default)

    fun setFloat(key: String, value: Float) = prefs.edit().putFloat(key, value).apply()
    fun getFloat(key: String, default: Float = 0f) = prefs.getFloat(key, default)

    fun remove(key: String) = prefs.edit().remove(key).apply()
    fun clear() = prefs.edit().clear().apply()

    var deviceId: String
        get() = getString("device_id")
        set(value) = setString("device_id", value)
}