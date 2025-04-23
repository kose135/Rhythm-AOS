package com.longlegsdev.rhythm.data.remote.interceptor

import android.content.Context
import com.longlegsdev.rhythm.util.RhythmPrefs
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val deviceId = RhythmPrefs.deviceId

        val newRequest = request.newBuilder()
            .addHeader("user-id", deviceId)
            .build()

        return chain.proceed(newRequest)
    }
}