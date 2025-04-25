package com.longlegsdev.rhythm.presentation.screen.common.card

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import com.longlegsdev.rhythm.presentation.screen.common.component.AlbumCoverImage
import com.longlegsdev.rhythm.util.Space
import com.longlegsdev.rhythm.util.click

@Composable
fun ChannelCard(
    title: String,
    coverImageUrl: String,
    description: String,
    size: Int? = null,
    likes: Int? = null,
    liked: Boolean? = null,
    width: Dp,
    onTrackItemClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .click { onTrackItemClick() }
            .wrapContentSize()
    ) {

        Box(
            modifier = Modifier
                .size(width),
        ) {

            AlbumCoverImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(5.dp)),
                url = coverImageUrl
            )

            if (liked != null) {
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .padding(end = 10.dp, bottom = 10.dp)
                        .align(Alignment.BottomEnd),
                    imageVector = if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "channel liked",
                    tint = Color.Red
                )
            }

        }

        Space(width = 8.dp)

        Column(
            modifier = Modifier
                .width(width)
                .padding(4.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .alignByBaseline()
                        .basicMarquee(iterations = Int.MAX_VALUE),
                    text = if (size == null) title else "$title · ${size}곡",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                )

                if (likes != null) {
                    Text(
                        modifier = Modifier
                            .alignByBaseline(),
                        text = "좋아요 ${likes}개",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Visible,
                    )
                }
            }

            Space(height = 3.dp)

            Text(
                modifier = Modifier,
                text = description,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

