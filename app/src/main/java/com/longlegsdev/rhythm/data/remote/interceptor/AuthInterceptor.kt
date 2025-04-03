package com.longlegsdev.rhythm.data.remote.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("user-id", "") ?: ""

        val newRequest = chain.request().newBuilder()
            .addHeader("user-id", userId)
            .build()

        return chain.proceed(newRequest)
    }
}