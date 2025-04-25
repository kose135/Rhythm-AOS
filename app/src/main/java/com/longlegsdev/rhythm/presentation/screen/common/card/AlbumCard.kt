package com.longlegsdev.rhythm.presentation.screen.common.card

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.util.FileType
import com.longlegsdev.rhythm.util.Space
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PalettePlugin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    albumUrl: String,
    title: String,
    artist: String,
    borderWidth: Dp = 15.dp,
    animationDuration: Int = 5000,
    shape: RoundedCornerShape = CircleShape,
    context: Context = LocalContext.current,
) {

    // 회전 애니메이션
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite Color Animation")
    val degrees by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Infinite Colors"
    )

    val firstDomainColor = MaterialTheme.colorScheme.background

    // 색상 및 이미지 상태
    var domainColor by remember { mutableStateOf(firstDomainColor) }
    var mutedColor by remember { mutableStateOf(Color.LightGray) }
    var vibrantColor by remember { mutableStateOf(Color.Gray) }
    var lightColor by remember { mutableStateOf(Color.White) }

    var albumBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var triedToExtract by remember { mutableStateOf(false) }

    // 앨범 아트 추출
    LaunchedEffect(albumUrl) {
        if ((albumUrl.endsWith(".mp4") || albumUrl.endsWith(".mp3")) && albumBitmap == null && !triedToExtract) {
            triedToExtract = true
            albumBitmap = extractAlbumArtFromUrl(context, albumUrl)
        }
    }

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val imageSize = maxWidth * 0.8f // card size

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .drawWithContent {
                        rotate(degrees = degrees) {
                            drawCircle(
                                brush = Brush.sweepGradient(
                                    listOf(
                                        domainColor,
                                        vibrantColor,
                                        lightColor,
                                        Color.White.copy(alpha = 0.6f),
                                        domainColor
                                    )
                                ),
                                radius = this.size.width / 2 + borderWidth.value,
                                blendMode = BlendMode.SrcIn,
                            )
                        }
                        drawContent()
                    },
                color = MaterialTheme.colorScheme.surface,
                shape = shape
            ) {
                // albumBitmap이 null이 아닌 경우 Bitmap을 사용하고, null인 경우 albumUrl 사용
                CoilImage(
                    modifier = Modifier
                        .background(domainColor)
                        .size(imageSize)
                        .clip(shape),
                    imageModel = { if (FileType.fromUrl(albumUrl) == FileType.IMAGE) albumUrl else albumBitmap },
                    component = rememberImageComponent {
                        +PalettePlugin { p ->
                            domainColor = p.dominantSwatch?.rgb?.let { Color(it) } ?: Color.Gray
                            mutedColor = p.mutedSwatch?.rgb?.let { Color(it) } ?: Color.Gray
                            vibrantColor = p.vibrantSwatch?.rgb?.let { Color(it) } ?: Color.Gray
                            lightColor =
                                p.lightVibrantSwatch?.rgb?.let { Color(it) } ?: Color.White
                        }
                    }
                )
            }

            Space(height = 30.dp)

            // music title
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                textAlign = TextAlign.Center,
            )

            // music artist
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee(),
                text = artist,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                textAlign = TextAlign.Center,
            )

        }
    }

}


suspend fun extractAlbumArtFromUrl(context: Context, url: String): Bitmap =
    withContext(Dispatchers.IO) {
        return@withContext try {
            Timber.d("Start extract album image")
            Timber.d("url: $url")
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(url, HashMap())
            val picture = retriever.embeddedPicture
            retriever.release()

            Timber.d("End extract album image")
            if (picture != null) {
                BitmapFactory.decodeByteArray(picture, 0, picture.size)
            } else {
                BitmapFactory.decodeResource(context.resources, R.drawable.img_cover)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to extract album art from MP3")
            BitmapFactory.decodeResource(context.resources, R.drawable.img_cover)
        }
    }