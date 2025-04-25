package com.longlegsdev.rhythm.presentation.screen.main.section.trackdetail

import android.R.attr.scaleX
import android.R.attr.scaleY
import android.R.attr.top
import android.R.attr.translationY
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.presentation.screen.common.component.AlbumCoverImage
import com.longlegsdev.rhythm.util.Space

@OptIn(ExperimentalMotionApi::class)
@Composable
fun TrackInfoSection(
    modifier: Modifier = Modifier,
    trackInfo: ChannelEntity,
    progress: Float
) {
    val columnId = "contentColumn"

    MotionLayout(
        start = ConstraintSet {
            val contentColumn = createRefFor(columnId)
            constrain(contentColumn) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                scaleX = 1f
                scaleY = 1f
                translationY = 0.dp
                alpha = 1f
            }
        },
        end = ConstraintSet {
            val contentColumn = createRefFor(columnId)
            constrain(contentColumn) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                scaleX = 0.4f
                scaleY = 0.4f
                translationY = (-100).dp
                alpha = 0.2f
            }
        },
        progress = progress,
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .layoutId(columnId)
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            AlbumCoverImage(
                modifier = Modifier
                    .size(250.dp)
                    .clip(RoundedCornerShape(5.dp)),
                url = trackInfo.url
            )

            Text(
                text = trackInfo.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .basicMarquee(iterations = Int.MAX_VALUE)
                    .padding(top = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Visible
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = if (trackInfo.liked)
                        painterResource(R.drawable.ic_like_filled)
                    else
                        painterResource(R.drawable.ic_like_outlined),
                    contentDescription = "Like"
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "${trackInfo.likes}")
            }

            Text(
                text = trackInfo.description,
                modifier = Modifier
                    .basicMarquee(iterations = Int.MAX_VALUE)
                    .padding(top = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Visible
            )
        }
    }
}