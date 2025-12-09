package com.example.fotogramapp.features.createpost

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class CreatePostViewModel(navController: NavController) : ViewModel() {
    // == State ==
    var message by mutableStateOf("")
        private set

    var image by mutableStateOf("")
        private set

    var location by mutableStateOf("")
        private set

    // == Handle Functions ==
    val handleMessage: (String) -> Unit = {newMessage ->
        message = newMessage
    }

    val handleImage: (String) -> Unit = {newImage ->
        image = newImage
    }

    val handlePublish: () -> Unit = {
        //TODO: Aggiungere logica di creazione di un nuovo post
        navController.popBackStack()
    }

}