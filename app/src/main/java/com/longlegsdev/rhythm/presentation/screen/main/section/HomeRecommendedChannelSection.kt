package com.longlegsdev.rhythm.presentation.screen.main.section


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.longlegsdev.rhythm.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.data.entity.ChannelEntity
import com.longlegsdev.rhythm.presentation.viewmodel.channel.state.ChannelListState
import com.longlegsdev.rhythm.util.Space
import com.longlegsdev.rhythm.util.click
import com.skydoves.landscapist.coil.CoilImage


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HomeRecommendedChannelSection(
    modifier: Modifier = Modifier,
    state: ChannelListState,
    onChannelClick: (Int) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier
            .background(Color.Transparent)
            .fillMaxSize()
    ) {
        val screenWidth = maxWidth
        val itemWidth = screenWidth * 2 / 3

        Column(
            modifier = modifier
                .background(Color.Transparent)
                .fillMaxSize(),
        ) {

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                text = stringResource(R.string.home_recommended),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )

            Space(height = 10.dp)

            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                state.errorMessage != null -> {
                    Text(
                        text = stringResource(R.string.err_network),
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    LazyRow(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        itemsIndexed(state.channels.toList()) { index, channel ->
                            ChannelItem(
                                channel = channel,
                                width = itemWidth,
                                onChannelItemClick = { channelId ->
                                    onChannelClick(channelId)
                                }
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun ChannelItem(
    channel: ChannelEntity,
    width: Dp,
    onChannelItemClick: (Int) -> Unit
) {
    val channelId = channel.id
    val url = channel.url
    val title = channel.title
    val description = channel.description
    val size = channel.size
    val likes = channel.likes
    val liked = channel.liked

    Column(
        modifier = Modifier
            .click { onChannelItemClick(channelId) }
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .size(width),
        ) {
            CoilImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp)),
                imageModel = url,
                placeHolder = ImageBitmap.imageResource(R.drawable.rhythm_cover),
                error = ImageBitmap.imageResource(R.drawable.rhythm_cover)
            )

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
                    text = "$title · ${size}곡",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                )

                Text(
                    modifier = Modifier
                        .alignByBaseline(),
                    text = "좋아요 ${likes}개",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Visible,
                )
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

//
//@Preview(showBackground = true)
//@Composable
//fun ChannelItemPreview(
//
//) {
//    val channel = ChannelEntity(
//        id = 1,
//        title = "태연",
//        url = "",
//        description = "태연 노래모음집",
//        size = 10,
//        likes = 10,
//        liked = true
//    )
//
//    ChannelItem(channel)
//}