package com.example.fotogramapp.features.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BentoInformation(modifier: Modifier = Modifier, spacing: Int = 15) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(spacing.dp),
    ) {
        // -- Biography & Followers & Following --
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(spacing.dp)
        ) {
            BentoContainer(
                Modifier
                    .fillMaxWidth(3 / 5f)
                    .fillMaxHeight()
            ) {
                Text(
                    text = "Un breve testo generato per te, conciso e semplice, ideale per test o prove rapide ora. Long Biograpy",
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "Biography",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalArrangement = Arrangement.spacedBy(spacing.dp)
            ) {
                BentoContainer(
                    Modifier.height(75.dp)
                ) {
                    Text(
                        text = "12",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "Followers",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                BentoContainer(
                    Modifier.height(75.dp)
                ) {
                    Text(
                        text = "12",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = "Following",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }

        // -- DoB & Posts
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(spacing.dp)
        ) {
            BentoContainer(
                Modifier
                    .fillMaxWidth(3 / 5f)
            ) {
                Text(
                    text = "16/12/2002",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Date of Birth",
                    style = MaterialTheme.typography.labelSmall
                )
            }

            BentoContainer(
                Modifier.height(75.dp)
            ) {
                Text(
                    text = "2",
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = "Posts",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}