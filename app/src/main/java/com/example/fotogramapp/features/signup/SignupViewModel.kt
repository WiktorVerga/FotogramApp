package com.example.fotogramapp.features.signup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.User
import com.example.fotogramapp.navigation.Discover
import com.example.testing_apis.model.RemoteDataSource
import kotlinx.coroutines.launch

class SignupViewModel(
    private val navController: NavController,
    private val userRepo: UserRepository,
    private val snackBarHostState: SnackbarHostState
) : ViewModel() {

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

    val handleImage: (String) -> Unit = { newImage ->
        image = newImage
    }

    val handleSignup: () -> Unit = {
        viewModelScope.launch {
            if (username != "" && biography != "" && dob != "" && image != "") {

                //Registrazione dell'Utente
                try {

                    val (userId, sessionId) = userRepo.userSignup(username, biography, dob, image)

                    userRepo.setLoggedUser(userId, sessionId)
                    navController.navigate(Discover)

                } catch (error: APIException) {
                    //Cattura di qualsiasi errore della userSignup
                    error.message?.let { snackBarHostState.showSnackbar(it) }
                }
            } else {
                snackBarHostState.showSnackbar("Please fill out all fields")
            }
        }
    }
}