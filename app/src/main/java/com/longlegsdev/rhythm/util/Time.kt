package com.longlegsdev.rhythm.util

import android.annotation.SuppressLint
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Time {

    val currentTime: String
        get() = this.currentTime("yyyy-MM-dd HH:mm:ss")

    fun currentTime(format: String): String {
        val zone = ZoneId.of("Asia/Seoul")
        val currentDateTime = LocalDateTime.now(zone)
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(format)

        return currentDateTime.format(formatter)
    }
}

@SuppressLint("DefaultLocale")
fun Int.toTimeFormat(): String {
    val minutes = this / 60
    val seconds = this % 60
    return String.format("%d:%02d", minutes, seconds)
}