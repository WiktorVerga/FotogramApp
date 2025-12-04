package com.example.fotogramapp.features.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fotogramapp.ui.components.post.PostCard
import com.example.fotogramapp.ui.components.post.PostList
import com.example.fotogramapp.ui.theme.FotogramTheme

@Composable
fun DiscoverPage(modifier: Modifier = Modifier) {
    PostList()
}

@Preview
@Composable
private fun DiscoverPagePrev() {
    FotogramTheme() {
        DiscoverPage()
    }
}