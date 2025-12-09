package com.example.fotogramapp.features.signup

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.fotogramapp.navigation.Discover

class SignupViewModel(navController: NavController) : ViewModel() {

    // == State ==
    var username by mutableStateOf("")
        private set

    var biography by mutableStateOf("")
        private set

    var dob by mutableStateOf("")
        private set

    var image by mutableStateOf("")
        private set

    // == Handle Functions ==

    val handleUsername: (String) -> Unit = {newUsername ->
        username = newUsername
    }

    val handleBiography: (String) -> Unit = {newBiography ->
        biography = newBiography
    }

    val handleDob: (String) -> Unit = {newDob ->
        dob = newDob
    }

    val handleImage: (String) -> Unit = {newImage ->
        image = newImage
    }

    val handleSignup: () -> Unit = {
        //TODO: Aggiungere logica di registrazione
        // Fare chiamate di rete ed inserire userId ritornato nel DataStore
        navController.navigate(Discover)
    }



}