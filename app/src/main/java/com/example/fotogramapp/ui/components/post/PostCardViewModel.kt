package com.example.fotogramapp.ui.components.post

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.navigation.MapPage
import com.example.fotogramapp.navigation.Profile
import com.mapbox.geojson.Point
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch
import okio.IOException

class PostCardViewModel(
    val navController: NavController,
    private val userRepo: UserRepository,
    private val postRepo: PostRepository,
    private val snackbarHostState: SnackbarHostState
) : ViewModel() {

    // == State ==
    var loading by mutableStateOf(true)
        private set

    var showMenu by mutableStateOf(false)
        private set

    val menuItems = mapOf<String, () -> Unit>(
        "Delete" to {
            handleDelete()
        }
    )

    // == Post Data ==
    var id by mutableStateOf(-1)
        private set
    var creatorUsername by mutableStateOf("")
        private set

    var creatorPicture by mutableStateOf<String?>("")
        private set

    private var creatorId by mutableStateOf(-1)
        private set
    var message by mutableStateOf("")
        private set

    var image by mutableStateOf("")
        private set

    var location by mutableStateOf<Point?>(null)
        private set
    var hasLocation by mutableStateOf(false)
        private set

    var isSuggested by mutableStateOf(false)
        private set

    var isCurrentUser by mutableStateOf(false)
        private set

    // == HandleFunctions ==

    val handleProfileOnClick = {
        navController.navigate(
            Profile(
                id = creatorId
            )
        )
    }

    val handleDelete = {
        viewModelScope.launch {
            try {
                postRepo.deletePost(postId = id)

                navController.popBackStack()
                navController.navigate(Profile(creatorId))
                snackbarHostState.showSnackbar("Post Deleted")

            } catch (e: APIException) {
                Log.d("PostCardViewModel", e.message ?: "Unknown Error")
            } catch (e: IOException) {
                snackbarHostState.showSnackbar(
                    "No Internet Connection",
                    duration = SnackbarDuration.Long
                )
            } catch (e: HttpRequestTimeoutException) {
                snackbarHostState.showSnackbar(
                    "Request Timeout",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    val handleLocation = {
        val location = location
        location?.let {
            navController.navigate(MapPage(location.longitude(), location.latitude()))
        }
    }

    val handleFollow = {
        viewModelScope.launch {
            try {

                userRepo.followUser(creatorId)

                isSuggested = false
                snackbarHostState.showSnackbar("Followed ${creatorUsername}")

            } catch (e: APIException) {
                Log.d("PostCardViewModel", e.message ?: "Unknown Error")
            } catch (e: IOException) {
                snackbarHostState.showSnackbar(
                    "No Internet Connection",
                    duration = SnackbarDuration.Long
                )
            } catch (e: HttpRequestTimeoutException) {
                snackbarHostState.showSnackbar(
                    "Request Timeout",
                    duration = SnackbarDuration.Long
                )
            }
        }
    }

    // == Methods ==

    fun toggleMenu() {
        showMenu = !showMenu
    }

    fun closeMenu() {
        showMenu = false
    }

    fun loadPostData(postId: Int) {
        viewModelScope.launch {
            loading = true

            try {
                val post = postRepo.getPost(postId)
                val author = userRepo.getUser(post.authorId)

                creatorUsername = author.username
                creatorPicture = author.profilePicture
                creatorId = author.id

                id = post.id
                message = post.message
                image = post.image
                location = post.location
                hasLocation = post.location != null
                isSuggested = !author.isYourFollowing
                isCurrentUser = userRepo.isLoggedUser(author.id)

                loading = false
            } catch (e: APIException) {
                Log.d("PostCardViewModel", e.message ?: "Unknown Error")
            } catch (e: IOException) {
            } catch (e: HttpRequestTimeoutException) {
                snackbarHostState.showSnackbar(
                    "Request Timeout"
                )
            }
        }
    }
}