package com.longlegsdev.rhythm.presentation.screen.common.card

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.presentation.screen.common.component.AlbumCoverImage
import com.longlegsdev.rhythm.util.click
import com.longlegsdev.rhythm.util.toTimeFormat
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun TrackMusicCard(
    musicId: Int,
    title: String,
    albumImageUrl: String,
    artist: String,
    duration: Long,
    onMusicItemClick: () -> Unit,
    isCurrent: Boolean,
) {

    Row(
        modifier = Modifier
            .click { onMusicItemClick() }
            .fillMaxWidth()
            .background(
                if (isCurrent)
                    MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
                else
                    Color.Transparent
            )
    ) {
        // image
        AlbumCoverImage(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(5.dp)),
            url = albumImageUrl,
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(7.dp)
        ) {
            // title
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Visible,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // artist & duration
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .basicMarquee(iterations = Int.MAX_VALUE),
                    text = "$artist · ${duration.toTimeFormat()}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                )

            }
        }
    }
}
