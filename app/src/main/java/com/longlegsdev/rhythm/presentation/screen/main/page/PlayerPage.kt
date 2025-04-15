package com.longlegsdev.rhythm.presentation.screen.main.page

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.AlbumCard
import com.longlegsdev.rhythm.presentation.screen.main.section.player.AlbumSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.PlayerSection
import com.longlegsdev.rhythm.presentation.screen.main.section.player.ProgressSection
import com.longlegsdev.rhythm.util.Space

@Composable
fun PlayerPage(
    padding: PaddingValues,
    sharedAlbumImage: @Composable () -> Unit,
) {
    val musics = listOf(
        MusicEntity(1, "I (Feat. 버벌진트)", "태연(TAEYEON)", "", 205, "가사1222", "http://192.168.0.2:8100/music/I.mp4", 5, true),
        MusicEntity(2, "Weekend", "태연(TAEYEON)", "", 234, "가사1333", "http://192.168.0.2:8100/music/Weekend.mp4", 5, false),
        MusicEntity(3, "제주도의 푸른 밤", "태연(TAEYEON)", "", 204, "가사14444", "http://192.168.0.2:8100/music/제주도의 푸른 밤.mp4", 5, false),
        MusicEntity(4, "한 페이지가 될 수 있게 Time of Our Life", "DAY6(데이식스)", "", 206, "가사166666", "http://192.168.0.2:8100/music/Time of Our Life.mp4", 5, true),
        MusicEntity(5, "Viva La Vida", "Coldplay", "", 242, "가사177777", "http://192.168.0.2:8100/music/Viva La Vida.mp4", 5, true),
        MusicEntity(6, "LILAC (라일락)", "IU(아이유)", "", 214, "88888888", "http://192.168.0.2:8100/music/LILAC.mp4", 5, false),
    )

    var num by remember { mutableStateOf(1) }
    val pagerState = rememberPagerState(
        initialPage = num,
        pageCount = { musics.size }
    )

    LaunchedEffect(key1 = num) {
        pagerState.animateScrollToPage(
            page = (num) % musics.size,
            animationSpec = tween(500)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(padding)
    ) {
        AlbumSection(
            pagerState,
            musics
        )

        Space(height = 10.dp)

        ProgressSection(
            currentTime = 32,
            duration = 205,
            seekTo = { }
        )

        Space(height = 10.dp)

        PlayerSection(
            pre = { },
            playPause = { },
            next = { },
        )

    }
}