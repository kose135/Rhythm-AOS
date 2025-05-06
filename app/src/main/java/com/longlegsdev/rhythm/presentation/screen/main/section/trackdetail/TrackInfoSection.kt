package com.longlegsdev.rhythm.presentation.screen.main.section.trackdetail

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.data.entity.TrackEntity
import com.longlegsdev.rhythm.presentation.screen.common.component.AlbumCoverImage
import com.longlegsdev.rhythm.util.Space
import com.longlegsdev.rhythm.util.click

@OptIn(ExperimentalMotionApi::class)
@Composable
fun TrackInfoSection(
    modifier: Modifier = Modifier,
    trackEntityInfo: TrackEntity,
    isFavorite: Boolean,
    progress: Float,
    onFavoriteClick: () -> Unit,
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
                url = trackEntityInfo.url
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = trackEntityInfo.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .basicMarquee(iterations = Int.MAX_VALUE)
                        .alignBy { it.measuredHeight / 2 },
                    maxLines = 1,
                    overflow = TextOverflow.Visible
                )

                Space(width = 5.dp)

                Icon(
                    painter = if (isFavorite)
                        painterResource(R.drawable.ic_favorite_filled)
                    else
                        painterResource(R.drawable.ic_favorite_outlined),
                    contentDescription = "Track Favorite Icon",
                    modifier = Modifier
                        .click { onFavoriteClick() }
                        .size(30.dp)
                        .alignBy { it.measuredHeight / 2 },
                )
            }

            Text(
                text = trackEntityInfo.description,
                modifier = Modifier
                    .basicMarquee(iterations = Int.MAX_VALUE)
                    .padding(top = 8.dp),
                maxLines = 2,
                overflow = TextOverflow.Visible
            )
        }
    }
}