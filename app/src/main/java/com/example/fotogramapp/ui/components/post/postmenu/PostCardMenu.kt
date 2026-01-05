package com.example.fotogramapp.ui.components.post.postmenu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PostCardMenu(modifier: Modifier = Modifier, items: Map<String, () -> Unit>) {
    ElevatedCard(
        modifier = modifier
            .width(IntrinsicSize.Min),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
        ) {
            items.forEach { item ->
                TextButton(
                    onClick = item.value
                ) {
                    Text(
                        text = item.key,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (item.key != items.keys.last())
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.tertiaryContainer)
            }
        }
    }
}