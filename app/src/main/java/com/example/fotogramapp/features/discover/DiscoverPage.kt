package com.example.fotogramapp.features.discover

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.navigation.CreatePost
import com.example.fotogramapp.ui.components.post.PostList
import com.example.fotogramapp.ui.theme.FotogramTheme

@Composable
fun DiscoverPage(modifier: Modifier = Modifier, navController: NavController) {

    val viewModel: DiscoverViewModel = viewModel()
    var totalDx by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    Box(
        modifier = Modifier
        .pointerInput(Unit) {
        detectDragGestures(
            onDrag = { change, dragAmount ->
                change.consume()
                val (dx, dy) = dragAmount

                //Controllo che lo swipe sia principalmente orizzontale
                if (kotlin.math.abs(dx) > 50f && kotlin.math.abs(dy / dx) < 0.4f) {
                    if (dx > 0) {
                        totalDx += dragAmount.x
                    }
                }
            },
            onDragEnd = {
                if (totalDx > 150f) {
                    navController.navigate(CreatePost)
                    Log.d("Swipe", "Swipe Right")
                }
            }
        )
    }
    ) {
        PostList(posts = viewModel.posts, navController = navController)
    }
}

@Preview
@Composable
private fun DiscoverPagePrev() {
    FotogramTheme() {
        DiscoverPage(navController = rememberNavController())
    }
}