package com.longlegsdev.rhythm.presentation.screen.main.section.player

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.util.click

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun PlayerSection(
    modifier: Modifier = Modifier,
    isPlay: Boolean = false,
    pre: () -> Unit,
    playPause: () -> Unit,
    next: () -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val iconSize = maxWidth * 0.12f // icon size

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 이전곡
            IconButton(
                onClick = pre,
                modifier = Modifier.size(iconSize)
            ) {
                Image(
                    painter = painterResource(R.drawable.btn_pre),
                    contentDescription = "pre music",
                    modifier = Modifier.size(iconSize)
                )
            }

            // 재생 및 일시중지
            IconButton(
                onClick = playPause,
                modifier = Modifier
                    .size(iconSize * 2)
                    .padding(4.dp)
            ) {

                Image(
                    painter = if(isPlay) painterResource(R.drawable.btn_pause) else painterResource(R.drawable.btn_play),
                    contentDescription = "play or pause",
                    modifier = Modifier.size(iconSize * 1.5f)
                )
            }

            // 다음곡
            IconButton(
                onClick = next,
                modifier = Modifier
                    .size(iconSize)
            ) {
                Image(
                    painter = painterResource(R.drawable.btn_next),
                    contentDescription = "next music",
                    modifier = Modifier.size(iconSize)
                )
            }
        }
    }
}