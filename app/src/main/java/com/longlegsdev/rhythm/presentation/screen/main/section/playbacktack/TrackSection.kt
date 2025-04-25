package com.longlegsdev.rhythm.presentation.screen.main.section.playbacktack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.data.entity.MusicEntity
import com.longlegsdev.rhythm.presentation.screen.common.card.TrackMusicCard
import timber.log.Timber

@Composable
fun TrackSection(
    musicList: List<MusicEntity>,
    currentIndex: Int,
    onMusicItemClick: (Int) -> Unit,
) {

    LazyColumn(
        modifier = Modifier
            .padding(top = 7.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        itemsIndexed(musicList) { index, music ->
            val musicId = music.id
            val title = music.title
            val albumImageUrl = music.album
            val artist = music.artist
            val duration = music.duration
            val liked = music.liked
            val isCurrent = index == currentIndex

            Timber.d("duration= $duration")

            TrackMusicCard(
                musicId = musicId,
                title = title,
                albumImageUrl = albumImageUrl,
                artist = artist,
                duration = duration,
                liked = liked,
                onMusicItemClick = {
                    onMusicItemClick(index)
                },
                isCurrent = isCurrent,
            )
        }

    }
}