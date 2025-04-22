package com.longlegsdev.rhythm.presentation.screen.common.component

import android.content.Context
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

@Composable
fun AlbumCoverImage(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    url: String,
    @DrawableRes loadingRes: Int? = R.drawable.ic_cover,
    @DrawableRes errorRes: Int? = R.drawable.ic_cover,
) {
    CoilImage(
        imageModel = {
            ImageRequest.Builder(context)
                .data(url)
                .memoryCachePolicy(CachePolicy.ENABLED)
                .diskCachePolicy(CachePolicy.ENABLED)
                .networkCachePolicy(CachePolicy.ENABLED)
                .precision(Precision.INEXACT)
                .build()
        },
        modifier = modifier,
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