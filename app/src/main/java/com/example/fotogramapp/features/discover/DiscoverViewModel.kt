package com.example.fotogramapp.features.discover

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
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
import okio.IOException

class DiscoverViewModel(
    private val navController: NavController,
    private val userRepo: UserRepository,
    private val postRepo: PostRepository,
    private val snackbarHostState: SnackbarHostState
) : ViewModel() {

    // == State ==
    var postIds by mutableStateOf(listOf<Int>())
        private set

    var offline by mutableStateOf(false)
        private set

    var maxPostId by mutableStateOf<Int?>(null)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    // == List Scroll State ==
    var firstVisibleItemIndex by mutableStateOf(0)
        private set
    var firstVisibleItemScrollOffset by mutableStateOf(0)
        private set


    // == Methods ==

    fun saveMaxPostId(maxPostId: Int) {
        this.maxPostId = maxPostId
    }

    fun loadFeed(limit: Int? = null, seed: Int? = null) {
        viewModelScope.launch {
            isRefreshing = true
            try {
                postIds += postRepo.getFeed(
                    maxPostId = maxPostId,
                    limit = limit,
                    seed = seed
                )
                offline = false
                isRefreshing = false
            } catch (e: APIException) {
                Log.d("DiscoverViewModel", e.message ?: "Unknown Error")
            } catch (e: ConnectTimeoutException) {
                snackbarHostState.showSnackbar("Request Timeout")
            } catch (e: IOException) {
                if (!offline) {
                    offline = true
                    snackbarHostState.showSnackbar("No Internet Connection")
                }
                offline = true
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