package com.longlegsdev.rhythm.util

fun String.firstUpper(): String {
    return this.trim().lowercase().replaceFirstChar { it.uppercase() }
}

fun Int.twoDigit(): String {
    return "%02d".format(this)
}

fun Int.threeDigit(): String {
    return "%03d".format(this)
}