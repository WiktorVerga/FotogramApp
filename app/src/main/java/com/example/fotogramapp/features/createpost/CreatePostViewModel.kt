package com.example.fotogramapp.features.createpost

import android.graphics.Bitmap
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.SettingsRepository
import com.mapbox.geojson.Point
import kotlinx.coroutines.launch

class CreatePostViewModel(
    navController: NavController,
    val database: AppDatabase,
    val settingsRepository: SettingsRepository
) : ViewModel() {
    val postRepo = PostRepository(database, settingsRepository)

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
        //TODO: Aggiungere logica di creazione di un nuovo post
        viewModelScope.launch {
            postRepo.addPost(message, image, location)
            navController.popBackStack()
        }
    }
}