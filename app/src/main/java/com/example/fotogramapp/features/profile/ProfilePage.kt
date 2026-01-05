package com.example.fotogramapp.features.profile

import PostCard
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fotogramapp.LocalPostRepository
import com.example.fotogramapp.LocalUserRepository
import com.example.fotogramapp.app.LocalNavController
import com.example.fotogramapp.app.LocalSnackbar
import com.example.fotogramapp.features.profile.components.BentoInformation
import com.example.fotogramapp.ui.components.offlineretry.OfflineRetry
import com.example.fotogramapp.ui.components.buttons.FollowButton
import com.example.fotogramapp.ui.components.buttons.PrimaryButton
import com.example.fotogramapp.ui.components.images.PrimaryImage
import com.example.fotogramapp.ui.components.title.LargeHeadline

@Composable
fun ProfilePage(modifier: Modifier = Modifier, userId: Int) {

    val userRepo = LocalUserRepository.current
    val postRepo = LocalPostRepository.current
    val navController = LocalNavController.current
    val snackbarHostState = LocalSnackbar.current

    val viewModel: ProfileViewModel = viewModel(
        factory =
            viewModelFactory {
                initializer {
                    ProfileViewModel(
                        userRepo = userRepo,
                        postRepo = postRepo,
                        navController = navController,
                        snackbarHostState = snackbarHostState
                    )
                }
            }
    )

    // == Lazy Column State ==
    val lazyColState = rememberLazyListState()

    val lastVisibleIndex = remember {
        derivedStateOf { lazyColState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
    }

    // == Launched Effects ==

    //Load User Data
    LaunchedEffect(Unit) {
        viewModel.loadUserData(userId)
    }

    // Trigger fetch when near the bottom
    LaunchedEffect(lastVisibleIndex.value) {
        val lastVisibleIndex = lazyColState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        val total = lazyColState.layoutInfo.totalItemsCount
        val threshold = total - 3

        Log.d("ProfilePage", "threshold: ${threshold}")
        if (threshold > 3) {
            viewModel.saveMaxPostId(viewModel.posts[viewModel.posts.lastIndex] - 1)
            if (lastVisibleIndex != null && lastVisibleIndex >= threshold) {
                Log.d("ProfilePage", "lastVisibleIndex: ${viewModel.maxPostId}")
                viewModel.loadPosts(userId)
            }
        }

    }


    if (viewModel.loading) {
        // == Loading Hanlding ==
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }

    } else {
        if (viewModel.offline) {
            // == Offline Handling ==
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                OfflineRetry() {
                    viewModel.loadUserData(userId)
                }
            }

        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = lazyColState
            ) {

                // == Profile Header ==
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                                .background(
                                    MaterialTheme.colorScheme.tertiaryContainer,
                                    CircleShape
                                )

                        ) {
                            PrimaryImage(image64 = viewModel.profilePicture, isPfp = true)
                        }
                        Column() {
                            Text(
                                text = if (viewModel.isCurrentUser) "Hello!" else "This is:",
                                style = MaterialTheme.typography.labelLarge,
                            )
                            Text(
                                text = viewModel.username,
                                style = MaterialTheme.typography.headlineMedium,
                            )
                        }
                    }

                    // == Bento Information ==
                    BentoInformation(
                        modifier = Modifier
                            .padding(vertical = 20.dp),
                        biograpy = viewModel.biography,
                        followers = viewModel.followersCount,
                        following = viewModel.followingCount,
                        dob = viewModel.dob,
                        postCounter = viewModel.postCount,
                    )

                    // == Follow / Edit Button ==
                    if (viewModel.isCurrentUser) {
                        PrimaryButton(
                            text = "Edit Profile",
                            onClick = viewModel.handleEditProfile
                        )
                    } else {
                        FollowButton(
                            isFollowing = viewModel.isFollowing,
                            onClick = viewModel.handleFollowToggle,
                        )
                    }

                    // == Posts Title ==
                    LargeHeadline("Posts \uD83C\uDF1F")
                }

                // == Posts ==
                itemsIndexed(viewModel.posts) { index, postId ->
                    PostCard(key = index.toString(), postId = postId)
                }

                // == Offline Posts Loading Handling ==
                item {
                    if (viewModel.offlinePosts) {
                        OfflineRetry(text = "Cannot load more Posts") {
                            viewModel.loadPosts(userId)
                        }
                    }
                }

                // == End Spacer ==
                item {
                    Box(modifier = Modifier.size(50.dp))
                }
            }
        }
    }
}

