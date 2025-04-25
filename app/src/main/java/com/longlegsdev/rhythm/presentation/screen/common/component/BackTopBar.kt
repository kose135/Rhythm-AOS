package com.longlegsdev.rhythm.presentation.screen.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.longlegsdev.rhythm.util.Space

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackClick: () -> Unit,
    backIcon: ImageVector = Icons.Default.ArrowBack,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(imageVector = backIcon, contentDescription = "뒤로가기")
        }

        Space(width = 10.dp)

        if (title.isNotEmpty()) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
@Preview
fun BackTopBarPreview() {
    BackTopBar(
        title = "뒤로가기",
        onBackClick = {}
    )
}