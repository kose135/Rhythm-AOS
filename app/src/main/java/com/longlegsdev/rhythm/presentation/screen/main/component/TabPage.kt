package com.longlegsdev.rhythm.presentation.screen.main.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.screen.main.tab.ChannelTab
import com.longlegsdev.rhythm.presentation.screen.main.tab.HomeTab
import com.longlegsdev.rhythm.presentation.screen.main.tab.StorageTab

enum class TabPage(
    @StringRes
    val titleResId: Int,
    @DrawableRes
    val iconResId: Int,
) {

    Channel(
        titleResId = R.string.tab_channel,
        iconResId = R.drawable.ic_channel,
    ),

    Home(
        titleResId = R.string.tab_home,
        iconResId = R.drawable.ic_home,
    ),

    Storage(
        titleResId = R.string.tab_storage,
        iconResId = R.drawable.ic_storage,
    );

    @Composable
    fun Screen() = when (this) {
        Channel -> ChannelTab()
        Home -> HomeTab()
        Storage -> StorageTab()
    }

}