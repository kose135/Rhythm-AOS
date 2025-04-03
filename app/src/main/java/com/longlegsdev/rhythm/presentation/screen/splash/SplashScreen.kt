package com.longlegsdev.rhythm.presentation.screen.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    backgroundColor: Color = Color.White,
    logo: ImageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
    duration: Int = 2000,
    tintColor: Color = Color.Gray,
    scale: Dp = 160.dp,
    todo: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) scale.value else 0f,
        animationSpec = tween(
            durationMillis = duration,
            easing = LinearEasing
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(duration.toLong())
        todo()
    }

    Splash(
        backgroundColor,
        tintColor,
        scaleAnim.value.dp,
        logo,
    )
}

@Composable
fun Splash(
    backgroundColor: Color,
    tintColor: Color,
    scale: Dp,
    logo: ImageVector,
) {

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(scale),
            imageVector = logo,
            contentDescription = "Logo Icon",
            tint = tintColor
        )
    }
}

@Composable
@Preview
fun SplashScreenPreview() {
    Splash(
        backgroundColor = Color.White,
        tintColor = Color.Gray,
        scale = 120.dp,
        logo = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
    )
}