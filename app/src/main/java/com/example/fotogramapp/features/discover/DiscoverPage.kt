package com.example.fotogramapp.features.discover

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.LocalAppDatabase
import com.example.fotogramapp.LocalDataStore
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.navigation.CreatePost
import com.example.fotogramapp.navigation.LocalNavController
import com.example.fotogramapp.ui.components.post.PostCard
import com.example.fotogramapp.ui.components.title.LargeHeadline
import com.example.fotogramapp.ui.theme.FotogramTheme

@Composable
fun DiscoverPage(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val db = LocalAppDatabase.current
    val settingsRepository = LocalDataStore.current

    val viewModel: DiscoverViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                DiscoverViewModel(database = db, settingsRepository = settingsRepository)
            }
        }
    )
    var totalDx by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        viewModel.loadPosts()
    }

    LazyColumn (
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
                        }
                    }
                )
            },
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // == Headline ==
        item {
            LargeHeadline("Discover your Feed \uD83D\uDD0D")
        }

        itemsIndexed(viewModel.posts) { index, post ->
            PostCard(key = index.toString(), post = post)
        }

        // == End Spacer ==
        item {
            Box(modifier = Modifier.size(50.dp))
        }
    }
}
