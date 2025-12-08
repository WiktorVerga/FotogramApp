package com.example.fotogramapp.features.discover

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fotogramapp.ui.components.post.PostList
import com.example.fotogramapp.ui.theme.FotogramTheme

@Composable
fun DiscoverPage(modifier: Modifier = Modifier) {

    val viewModel: DiscoverViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    PostList(posts = viewModel.posts)
}

@Preview
@Composable
private fun DiscoverPagePrev() {
    FotogramTheme() {
        DiscoverPage()
    }
}