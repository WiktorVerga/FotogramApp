package com.example.fotogramapp.features.createpost

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.SettingsRepository
import com.mapbox.geojson.Point
import io.ktor.client.plugins.HttpRequestTimeoutException
import kotlinx.coroutines.launch
import okio.IOException

class CreatePostViewModel(
    private val navController: NavController,
    private val postRepo: PostRepository,
    private val snackbarHostState: SnackbarHostState

) : ViewModel() {

    // == State ==
    var message by mutableStateOf("")
        private set

    var image by mutableStateOf<String>("")
        private set

    var location by mutableStateOf<Point?>(null)
        private set

    // == Handle Functions ==
    val handleMessage: (String) -> Unit = {newMessage ->
        message = newMessage
    }

    val handleImage: (String) -> Unit = { newImage ->
        image = newImage
    }


    val handleLocation: (Point) -> Unit = { newLocation ->
        location = newLocation
    }

    val handlePublish: () -> Unit = {
        viewModelScope.launch {
            if (message != "" && image != "") {
                try {
                    postRepo.addPost(message, image, location)
                    navController.popBackStack()
                    snackbarHostState.showSnackbar("Post Published")
                } catch (e: APIException) {
                    Log.d("CreatePostViewModel", e.message ?: "Unknown Error")
                } catch (e: IOException) {
                    snackbarHostState.showSnackbar("No Internet Connection")
                } catch (e: HttpRequestTimeoutException) {
                    snackbarHostState.showSnackbar("Timeout Error")
                }
            } else {
                snackbarHostState.showSnackbar("Please fill out all non-optional fields")
            }

        }
    }
}