package com.example.fotogramapp.features.signup

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.data.utils.toBase64
import com.example.fotogramapp.domain.model.User
import com.example.fotogramapp.navigation.Discover
import kotlinx.coroutines.launch

class SignupViewModel(
    private val navController: NavController,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    // == State ==
    var username by mutableStateOf("")
        private set

    var biography by mutableStateOf("")
        private set

    var dob by mutableStateOf("")
        private set

    var image by mutableStateOf<String>("")
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

    val handleImage: (String) -> Unit = { newImage ->
        image = newImage
    }

    val handleSignup: () -> Unit = {
        //TODO: Aggiungere logica di registrazione
        // Fare chiamate di rete ed inserire userId ritornato nel DataStore
        Log.d("Signup", "Username: $username, Biography: $biography, DOB: $dob, Image: $image")

        viewModelScope.launch {
            //TODO: Inserisci qui metodo di chiamata di rete che restituisce userId e SessionID
            val userId = 1
            settingsRepository.setLoggedUser(userId, "Hard-Coded-sessionId")

        }
        navController.navigate(Discover)
    }



}