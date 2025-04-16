package com.longlegsdev.rhythm.presentation.screen.main.section.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.presentation.screen.common.component.ProgressBar
import com.longlegsdev.rhythm.util.Space
import com.longlegsdev.rhythm.util.toTimeFormat

@Composable
fun ProgressSection(
    currentTime: Int,
    duration: Int,
    seekTo: (Int) -> Unit,
) {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(horizontal = 15.dp)
            .fillMaxWidth()
    ) {

        ProgressBar(
            height = 2.dp,
            current = currentTime,
            duration = duration,
            changeEnable = true,
            seekTo = { seekTo(it) },
        )

        Space(height = 15.dp)

        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = currentTime.toTimeFormat(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )

            Text(
                text = duration.toTimeFormat(),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black
            )

        }
    }
}