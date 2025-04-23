package com.longlegsdev.rhythm.presentation.screen.main.section.lyrics

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.R
import com.longlegsdev.rhythm.presentation.viewmodel.player.event.AlignmentEvent
import com.longlegsdev.rhythm.util.Space

@Composable
fun AlignSection(
    align: AlignmentEvent,
    onAlignClick: (AlignmentEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        // align left
        IconButton(
            onClick = { onAlignClick(AlignmentEvent.Left) },
            modifier = Modifier
                .background(Color.Transparent),
        ) {
            Image(
                modifier = Modifier
                    .size(25.dp),
                painter = painterResource(R.drawable.ic_align_left),
                contentDescription = "align left",
                colorFilter = ColorFilter.tint(
                    if (align.isSelected(AlignmentEvent.Left))
                        Color.Black
                    else
                        Color.Gray
                ),
            )
        }

        Space(width = 7.dp)

        // align center
        IconButton(
            onClick = { onAlignClick(AlignmentEvent.Center) },
            modifier = Modifier
                .background(Color.Transparent),
        ) {
            Image(
                modifier = Modifier
                    .size(25.dp),
                painter = painterResource(R.drawable.ic_align_center),
                contentDescription = "align center",
                colorFilter = ColorFilter.tint(
                    if (align.isSelected(AlignmentEvent.Center))
                        Color.Black
                    else
                        Color.Gray
                ),
            )
        }

    }

}