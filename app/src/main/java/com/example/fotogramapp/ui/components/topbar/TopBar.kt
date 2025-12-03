package com.example.fotogramapp.ui.components.topbar

import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text("Small Top App Bar")
        }
    )
}