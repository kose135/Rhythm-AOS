package com.longlegsdev.rhythm.presentation.screen.main.section.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.util.click

@Composable
fun PlayerSection(
    pre: () -> Unit,
    playPause: () -> Unit,
    next: () -> Unit,
) {
// 재생 컨트롤 버튼
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = pre,
            modifier = Modifier.size(48.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.btn_pre),
                contentDescription = "pre music",
                modifier = Modifier.size(32.dp)
            )
        }

        IconButton(
            onClick = playPause,
            modifier = Modifier
                .size(64.dp)
                .padding(4.dp)
        ) {

            Image(
                painter = painterResource(R.drawable.btn_play),
                contentDescription = "play or pause",
                modifier = Modifier.size(64.dp)
            )
        }

        IconButton(
            onClick = next,
            modifier = Modifier
                .size(48.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.btn_pre),
                contentDescription = "next music",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}