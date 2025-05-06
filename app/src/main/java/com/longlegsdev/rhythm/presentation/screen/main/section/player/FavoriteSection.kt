package com.longlegsdev.rhythm.presentation.screen.main.section.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.R

@Composable
fun FavoriteSection(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
) {

    val iconSize = 35.dp

    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {

        IconButton(
            onClick = { onFavoriteClick() },
            modifier = Modifier.size(iconSize)
        ) {
            Image(
                modifier = Modifier.size(iconSize),
                painter = if (isFavorite) painterResource(R.drawable.ic_favorite_filled) else painterResource(
                    R.drawable.ic_favorite_outlined
                ),
                contentDescription = "pre music",
            )
        }
    }

}