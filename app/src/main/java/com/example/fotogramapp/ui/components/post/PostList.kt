package com.example.fotogramapp.ui.components.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.ui.components.post.postcard.PostCard

@Composable
fun PostList(modifier: Modifier = Modifier, posts: List<Post>, navController: NavController) {
    val viewModel: PostListViewModel = viewModel()

    val lazyColState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewModel.firstVisibleItemIndex,
        initialFirstVisibleItemScrollOffset = viewModel.firstVisibleItemScrollOffset
    )

    LazyColumn(
        state = lazyColState,
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(posts) { index, post ->
            PostCard(key = index.toString(), post = post, navController = navController)
        }

        // == End Spacer ==
        item {
            Box(modifier = Modifier.size(50.dp))
        }
    }
}