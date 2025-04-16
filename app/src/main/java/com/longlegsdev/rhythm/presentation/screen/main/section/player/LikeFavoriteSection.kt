package com.longlegsdev.rhythm.presentation.screen.main.section.player

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.screen.common.component.ProgressBar
import com.longlegsdev.rhythm.util.Space
import com.longlegsdev.rhythm.util.Time.currentTime
import com.longlegsdev.rhythm.util.toTimeFormat

@Composable
fun LikeFavoriteSection(
    isLike: Boolean,
    likeCount: Int,
    isFavorite: Boolean,
    onLikeClick: (Boolean) -> Unit,
    onFavoriteClick: (Boolean) -> Unit,
) {

    val iconSize = 35.dp

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        Row(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            IconButton(
                onClick = { onLikeClick(!isLike) },
                modifier = Modifier.size(iconSize)
            ) {
                Image(
                    modifier = Modifier.size(iconSize),
                    painter = if (isLike) painterResource(R.drawable.ic_like_filled) else painterResource(
                        R.drawable.ic_like_outlined
                    ),
                    contentDescription = "pre music",
                )
            }

            Space(width = 15.dp)

            Text(
                text = "$likeCount",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                fontSize = 20.sp
            )

        }

        Space(width = 15.dp)

        IconButton(
            onClick = { onFavoriteClick(!isFavorite) },
            modifier = Modifier.size(iconSize)
        ) {
            Image(
                modifier = Modifier.size(iconSize),
                painter = if (isFavorite) painterResource(R.drawable.ic_favorite_filled) else painterResource(
                    R.drawable.ic_favorite_outlined
                ),
                contentDescription = "pre music",
            )
        }
    }

}