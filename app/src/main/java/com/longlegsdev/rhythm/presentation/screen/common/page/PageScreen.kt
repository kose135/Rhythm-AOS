package com.longlegsdev.rhythm.presentation.screen.common.page

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.screen.main.page.ChannelPage
import com.longlegsdev.rhythm.presentation.screen.main.page.HomePage
import com.longlegsdev.rhythm.presentation.screen.main.page.LyricsPage
import com.longlegsdev.rhythm.presentation.screen.main.page.PlayerPage
import com.longlegsdev.rhythm.presentation.screen.main.page.StoragePage
import com.longlegsdev.rhythm.presentation.screen.main.page.TrackPage

sealed class PageScreen(@StringRes val title: Int, @DrawableRes val icon: Int?) {
    object Channel : PageScreen(R.string.tab_channel, R.drawable.ic_channel)
    object Home : PageScreen(R.string.tab_home, R.drawable.ic_home)
    object Storage : PageScreen(R.string.tab_storage, R.drawable.ic_storage)

    object Player : PageScreen(R.string.tab_player, null)
    object Track : PageScreen(R.string.tab_track, null)
    object Lyrics : PageScreen(R.string.tab_lyrics, null)

    @Composable
    fun MainContent() = when (this) {
        Channel -> ChannelPage()
        Home -> HomePage()
        Storage -> StoragePage()
        else -> HomePage()
    }

    @Composable
    fun MusicContent() = when (this) {
        Player -> PlayerPage()
        Track -> TrackPage()
        Lyrics -> LyricsPage()
        else -> PlayerPage()
    }
}
