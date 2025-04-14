package com.longlegsdev.rhythm.presentation.screen.common.card

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.data.entity.FavoriteMusicEntity
import com.longlegsdev.rhythm.data.entity.RecentMusicEntity
import com.longlegsdev.rhythm.util.Space
import com.longlegsdev.rhythm.util.click
import com.longlegsdev.rhythm.util.toTimeFormat
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun FavoriteMusicCard(
    modifier: Modifier = Modifier,
    musicId: Int,
    title: String,
    albumImageUrl: String,
    artist: String,
    duration: Int,
    onFavoriteMusicClick: (Int) -> Unit = {}
) {

    Column(
        modifier = modifier
            .width(170.dp)
            .click { onFavoriteMusicClick(musicId) }
            .padding(end = 12.dp),
        verticalArrangement = Arrangement.Center
    ) {

        CoilImage(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(5.dp)),
            imageModel = albumImageUrl,
            placeHolder = ImageBitmap.imageResource(R.drawable.rhythm_cover),
            error = ImageBitmap.imageResource(R.drawable.rhythm_cover)
        )

        Space(height = 6.dp)

        Text(
            modifier = Modifier
                .basicMarquee(iterations = Int.MAX_VALUE),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Visible
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = artist,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = duration.toTimeFormat(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.DarkGray
            )
        }
    }
}