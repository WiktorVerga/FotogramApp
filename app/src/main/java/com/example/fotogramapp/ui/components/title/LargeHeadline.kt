package com.example.fotogramapp.ui.components.title

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text

@Composable
fun LargeHeadline(text: String, modifier: Modifier = Modifier) {
    Text(text,
        modifier = Modifier
            .padding(top = 50.dp, bottom = 20.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.headlineLarge
    )
}