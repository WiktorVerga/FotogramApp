package com.example.fotogramapp.features.map

import android.Manifest
import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fotogramapp.data.remote.APIException

import com.example.fotogramapp.data.repository.MapRepository
import com.example.fotogramapp.data.repository.PostLocation
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.data.utils.toBitmap
import com.mapbox.common.location.Location
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import io.ktor.client.network.sockets.ConnectTimeoutException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException

class MapPageViewModel(
    private val context: Context,
    private val postRepo: PostRepository,
    private val userRepo: UserRepository

): ViewModel() {

    val mapRepo = MapRepository()


    // == State ==
    var loading by mutableStateOf(true)
        private set

    var offline by mutableStateOf(false)
        private set


    var hasPermission by mutableStateOf(false)
        private set

    var centerLocation by mutableStateOf<Point?>(null)
        private set

    var postLocations by mutableStateOf<List<PostLocation>?>(null)

    // == Handle Functions ==

    val onLauncherResult = {granted: Boolean ->
        hasPermission = granted
    }

    // == Methods ==
    fun checkPermissions() {
        hasPermission = mapRepo.checkLocationPermission(context)
    }

    fun setGivenLocation(postLocation: Point) {
        centerLocation = postLocation
    }

    fun loadMappedPosts() {
        viewModelScope.launch {
            try {
                loading = true
                val postIds = postRepo.getMappedLoadedPosts()
                postLocations = postIds.map {
                    val post = postRepo.getPost(it)
                    val author = userRepo.getUser(post.authorId)
                    PostLocation(
                        coordinate = post.location!!,
                        name = author.username,
                        image = post.image
                    )
                }
                offline = false
                loading = false
            } catch (e: Exception) {
                Log.d("MapPageViewModel", e.message ?: "Unknown Error")
                offline = true
                delay(500)
                loading = false
            }
        }
    }


}