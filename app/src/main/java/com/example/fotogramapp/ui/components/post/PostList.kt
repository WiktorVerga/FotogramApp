package com.example.fotogramapp.ui.components.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.ui.components.post.postcard.PostCard

@Composable
fun PostList(modifier: Modifier = Modifier, posts: List<Post>) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        itemsIndexed(posts) { index, post ->
            PostCard(key = index.toString(), post = post)
        }
    }
}