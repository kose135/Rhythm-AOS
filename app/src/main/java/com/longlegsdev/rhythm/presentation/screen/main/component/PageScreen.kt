package com.longlegsdev.rhythm.presentation.screen.main.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.screen.main.page.ChannelPage
import com.longlegsdev.rhythm.presentation.screen.main.page.HomePage
import com.longlegsdev.rhythm.presentation.screen.main.page.PlayerPage
import com.longlegsdev.rhythm.presentation.screen.main.page.StoragePage

sealed class PageScreen(@StringRes val title: Int, @DrawableRes val icon: Int) {
    object Channel : PageScreen(R.string.tab_channel, R.drawable.ic_channel)
    object Home : PageScreen(R.string.tab_home, R.drawable.ic_home)
    object Storage : PageScreen(R.string.tab_storage, R.drawable.ic_storage)
    object Player : PageScreen(0, 0)

    @Composable
    fun Content() = when (this) {
        Channel -> ChannelPage()
        Home -> HomePage()
        Storage -> StoragePage()
        else -> HomePage()
    }
}
