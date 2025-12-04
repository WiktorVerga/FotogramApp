package com.example.fotogramapp.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fotogramapp.ui.theme.FotogramTheme
import java.util.Locale

@Composable
fun PrimaryButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
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
            text = text.uppercase(Locale.ROOT),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun PrimaryButtonPrev() {
    FotogramTheme {
        PrimaryButton(text = "Primary Button", onClick = {})
    }
}