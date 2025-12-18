package com.example.fotogramapp.features.discover

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.UserRepository
import io.ktor.client.network.sockets.ConnectTimeoutException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val navController: NavController,
    private val userRepo: UserRepository,
    private val postRepo: PostRepository
) : ViewModel() {

    // == State ==
    var postIds by mutableStateOf(listOf<Int>())
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    // == List Scroll State ==
    var firstVisibleItemIndex by mutableStateOf(0)
        private set
    var firstVisibleItemScrollOffset by mutableStateOf(0)
        private set


    // == Methods ==

    fun loadFeed(maxPostId: Int? = null, limit: Int? = null, seed: Int? = 11) {
        viewModelScope.launch {
            try {
                postIds += postRepo.getFeed(
                    maxPostId = maxPostId,
                    limit = limit,
                    seed = seed
                )
            } catch (e: APIException) {
                Log.d("DiscoverViewModel", e.message ?: "Unknown Error")
            } catch (e: ConnectTimeoutException) {

            }
        }
    }

    fun refreshFeed() {
        if (isRefreshing) return

        viewModelScope.launch {
            isRefreshing = true
            postIds = emptyList()
            delay(1000)
            loadFeed()
            isRefreshing = false
        }
    }

}