package com.longlegsdev.rhythm.presentation.screen.common.page

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.screen.main.page.TrackPage
import com.longlegsdev.rhythm.presentation.screen.main.page.HomePage
import com.longlegsdev.rhythm.presentation.screen.main.page.LyricsPage
import com.longlegsdev.rhythm.presentation.screen.main.page.PlayerPage
import com.longlegsdev.rhythm.presentation.screen.main.page.StoragePage
import com.longlegsdev.rhythm.presentation.screen.main.page.PlaybackTrackPage

sealed class PageScreen(@StringRes val title: Int, @DrawableRes val icon: Int?) {
    object Track : PageScreen(R.string.tab_tracks, R.drawable.ic_track)
    object Home : PageScreen(R.string.tab_home, R.drawable.ic_home)
    object Storage : PageScreen(R.string.tab_storage, R.drawable.ic_storage)

    object Player : PageScreen(R.string.tab_player, null)
    object PlaybackTrack : PageScreen(R.string.tab_track, null)
    object Lyrics : PageScreen(R.string.tab_lyrics, null)

    @Composable
    fun MainContent() = when (this) {
        Track -> TrackPage()
        Home -> HomePage()
        Storage -> StoragePage()
        else -> HomePage()
    }

    @Composable
    fun MusicContent() = when (this) {
        Player -> PlayerPage()
        PlaybackTrack -> PlaybackTrackPage()
        Lyrics -> LyricsPage()
        else -> PlayerPage()
    }
}
