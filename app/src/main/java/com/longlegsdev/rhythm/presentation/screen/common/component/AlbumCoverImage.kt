package com.longlegsdev.rhythm.presentation.screen.common.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Precision
import com.longlegsdev.rhythm.R
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.Dispatchers

@Composable
fun AlbumCoverImage(
    modifier: Modifier = Modifier,
    url: String,
    @DrawableRes loadingRes: Int? = R.drawable.img_cover,
    @DrawableRes errorRes: Int? = R.drawable.img_cover,
) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    CoilImage(
        modifier = modifier,
        imageRequest = {
            ImageRequest.Builder(context)
                .data(url)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .networkCachePolicy(CachePolicy.ENABLED)
                .size(300, 300)
                .precision(Precision.INEXACT)
                .crossfade(100)
                .allowHardware(false)
                .dispatcher(Dispatchers.IO)
                .lifecycle(lifecycleOwner)
                .build()
        },
        loading = {
            loadingRes?.let {
                Image(
                    modifier = modifier,
                    painter = painterResource(id = it),
                    contentDescription = "Loading Image"
                )
            }
        },
        failure = {
            errorRes?.let {
                Image(
                    modifier = modifier,
                    painter = painterResource(id = it),
                    contentDescription = "Error Image"
                )
            }
        }
    )
}