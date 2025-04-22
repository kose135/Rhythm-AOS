package com.longlegsdev.rhythm.presentation.screen.main.section.main

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.screen.common.component.AlbumCoverImage
import com.longlegsdev.rhythm.presentation.screen.common.component.ProgressBar
import com.longlegsdev.rhythm.util.Space
import com.longlegsdev.rhythm.util.click

@Composable
fun MiniPlayBarSection(
    height: Dp = 56.dp,
    isPlay: Boolean = false,
    imageUrl: String = "",
    title: String = "",
    artist: String = "",
    currentPosition: Long,
    bufferedPosition: Long,
    duration: Long,
    onPlayPauseClick: () -> Unit,
    onMiniPlayerBarClick: () -> Unit,
) {
    val progressBarHeight = 4.dp

    Column(
        modifier = Modifier
            .height(height + progressBarHeight)
            .click(onMiniPlayerBarClick)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // album image
            AlbumCoverImage(
                modifier = Modifier
                    .size(height)
                    .clip(RoundedCornerShape(5.dp)),
                url = imageUrl
            )

            Space(width = 8.dp)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                // music title text
                Text(
                    text = title,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    softWrap = false,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier
                        .basicMarquee()
                        .weight(1f)
                )

                // music artist text
                Text(
                    text = artist,
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodySmall,
                    softWrap = false,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                    modifier = Modifier
                        .basicMarquee()
                        .weight(1f)
                )

            }

            // play or pause button
            Icon(
                painter = if(isPlay) painterResource(R.drawable.btn_pause) else painterResource(R.drawable.btn_play),
                contentDescription = "Play or Pause Button",
                modifier = Modifier
                    .padding(5.dp)
                    .size(height)
                    .clickable { onPlayPauseClick() },
                tint = Color.White
            )
        }

        // progress bar
        ProgressBar(
            progress = currentPosition,
            secondaryProgress = bufferedPosition,
            total = duration,
            height = progressBarHeight,
            seekTo = { }
        )
    }
}

