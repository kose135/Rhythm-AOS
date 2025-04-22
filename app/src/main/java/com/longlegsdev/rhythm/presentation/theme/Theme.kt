package com.longlegsdev.rhythm.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF7F39FB),       // 밝은 보라 (포인트 버튼 등)
    onPrimary = Color.White,           // 보라색 위 텍스트

    background = Color(0xFF7F39FB).copy(alpha = 0.8f),     // 전체 배경
    onBackground = Color(0xFF1E1E1E),   // 배경 위 텍스트

    surface = Color(0xFFE0E0E0),        // 카드, 컨트롤 배경
    onSurface = Color(0xFF212121),      // 카드 위 텍스트

    error = Color(0xFFB00020)           // 에러 상태
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFB388FF),       // 밝은 보라 (포인트 버튼 등)
    onPrimary = Color.Black,           // 보라색 위 텍스트

    background = Color(0xFF0D0E21).copy(alpha = 0.8f),     // 다크 블루/퍼플 계열 배경
    onBackground = Color(0xFFEDEDED),   // 배경 위 텍스트

    surface = Color(0xFF1C1C2E),        // 카드나 패널 배경
    onSurface = Color(0xFFF6F6F6),      // 카드 위 텍스트

    error = Color(0xFFFF6F61)           // 에러 (약간 형광 주황)
)

@Composable
fun RhythmAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}