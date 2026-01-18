package com.example.fotogramapp.features.discover

import PostCard
import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.fotogramapp.app.LocalSnackbar
import com.example.fotogramapp.navigation.CreatePost
import com.example.fotogramapp.ui.components.offlineretry.OfflineRetry
import com.example.fotogramapp.ui.components.title.LargeHeadline
import kotlin.math.abs

@Composable
fun DiscoverPage(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val postRepo = LocalPostRepository.current
    val snackbarHostState = LocalSnackbar.current

    val viewModel: DiscoverViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                DiscoverViewModel(
                    postRepo = postRepo,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    )

    // == State for Scrolling Effect =
    var totalDx by remember { mutableStateOf(0f) }

    val lazyColState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewModel.firstVisibleItemIndex,
        initialFirstVisibleItemScrollOffset = viewModel.firstVisibleItemScrollOffset
    )

    val lastVisibleIndex = remember {
        derivedStateOf { lazyColState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
    }

    // == Launch Effect ==

    //Load Feed
    LaunchedEffect(Unit) {
        viewModel.loadFeed()
    }

    // Trigger fetch when near the bottom
    LaunchedEffect(lastVisibleIndex.value) {
        val lastVisibleIndex = lazyColState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        val total = lazyColState.layoutInfo.totalItemsCount
        val threshold = total - 3

        if (threshold > 3) {
            viewModel.saveMaxPostId(viewModel.postIds[viewModel.postIds.lastIndex]-1)
            if (lastVisibleIndex != null && lastVisibleIndex >= threshold) {
                Log.d("DiscoverViewModel", "lastVisibleIndex: ${viewModel.maxPostId}")
                viewModel.loadFeed()
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
                    .fillMaxWidth()
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
                    }
                    .offset(x = totalDx.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = lazyColState
            ) {

                // == Headline ==
                item {
                    LargeHeadline("Discover your Feed \uD83D\uDD0D")
                }

                // == Posts ==
                itemsIndexed(viewModel.postIds) { index, postId ->
                    PostCard(key = index.toString(), postId = postId)
                }

                if (!viewModel.isRefreshing) {
                    // == End Section & Offline Handling ==
                    if (!viewModel.offline) {
                        item {
                            Text(
                                text = "You Finished your Feed!",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier
                                    .padding(bottom = 50.dp)
                            )

                        }
                    } else {
                        item {
                            OfflineRetry(modifier = Modifier.padding(vertical = 15.dp), text = "Cannot refresh Feed") {
                                viewModel.loadFeed()
                            }
                        }
                    }
                }
            }
        }
}
