package com.example.fotogramapp.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun FollowButton(modifier: Modifier = Modifier, isFollowing: Boolean = true, onClick: () -> Unit) {
    if (isFollowing) {
        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
            )
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                text = "following".uppercase(Locale.ROOT),
                style = MaterialTheme.typography.labelLarge
            )
        }
    } else {
        Button(
            modifier = modifier
                .fillMaxWidth(),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.tertiary,
            )
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp),
                text = "follow".uppercase(Locale.ROOT),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}