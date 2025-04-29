package com.longlegsdev.rhythm.presentation.screen.common.component

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.LocalLifecycleOwner
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
                .memoryCachePolicy(CachePolicy.ENABLED)         // 메모리 캐시 사용
                .diskCachePolicy(CachePolicy.ENABLED)           // 디스크 캐시 사용
                .networkCachePolicy(CachePolicy.ENABLED)        // 네트워크 캐시 사용
                .precision(Precision.INEXACT)                   // 부정확한 해상도 요청 (속도↑)
                .crossfade(true)                                // 부드러운 이미지 전환
                .allowHardware(true)                            // 하드웨어 가속 사용
                .dispatcher(Dispatchers.IO)                     // 백그라운드 스레드에서 로드
                .lifecycle(lifecycleOwner)                      // 생명 주기 연결
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