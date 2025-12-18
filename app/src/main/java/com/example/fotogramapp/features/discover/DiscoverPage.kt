package com.example.fotogramapp.features.discover

import PostCard
import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.example.fotogramapp.LocalPostRepository
import com.example.fotogramapp.LocalUserRepository
import com.example.fotogramapp.app.LocalNavController
import com.example.fotogramapp.navigation.CreatePost
import com.example.fotogramapp.ui.components.title.LargeHeadline
import kotlin.math.abs

@Composable
fun DiscoverPage(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val userRepo = LocalUserRepository.current
    val postRepo = LocalPostRepository.current

    val viewModel: DiscoverViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                DiscoverViewModel(
                    navController = navController,
                    userRepo = userRepo,
                    postRepo = postRepo
                )
            }
        }
    )

    var totalDx by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        viewModel.loadFeed()
    }

    val lazyColState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewModel.firstVisibleItemIndex,
        initialFirstVisibleItemScrollOffset = viewModel.firstVisibleItemScrollOffset
    )

    val lastVisibleIndex = remember {
        derivedStateOf { lazyColState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
    }

    // Trigger fetch when near the bottom
    LaunchedEffect(lastVisibleIndex.value) {
        val lastVisibleIndex = lazyColState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        val total = lazyColState.layoutInfo.totalItemsCount
        val threshold = total - 3

        if (threshold > 1) {
            if (lastVisibleIndex != null && lastVisibleIndex >= threshold) {
                Log.d("DiscoverPage", "lastVisibleIndex: ${viewModel.postIds[viewModel.postIds.lastIndex]-1}")
                viewModel.loadFeed(maxPostId = viewModel.postIds[viewModel.postIds.lastIndex]-1)
            }
        }

    }


    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = viewModel.isRefreshing,
        onRefresh = { viewModel.refreshFeed() },
    ) {
        LazyColumn (
            modifier = Modifier
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            val (dx, dy) = dragAmount

                            //Controllo che lo swipe sia principalmente orizzontale
                            if (abs(dx) > 50f && abs(dy / dx) < 0.4f) {
                                if (dx > 0) {
                                    totalDx += dragAmount.x
                                }
                            }
                        },
                        onDragEnd = {
                            if (totalDx > 150f) {
                                navController.navigate(CreatePost)
                            }
                        }
                    )
                },
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyColState
        ) {

            // == Headline ==
            item {
                LargeHeadline("Discover your Feed \uD83D\uDD0D")
            }


            itemsIndexed(viewModel.postIds) { index, postId ->
                PostCard(key = index.toString(), postId = postId)
            }


            // == End Spacer ==
            item {
                Box(modifier = Modifier.size(50.dp))
            }
        }
    }
}
