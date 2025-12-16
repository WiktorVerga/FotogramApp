package com.example.fotogramapp.features.signup

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.domain.model.User
import com.example.fotogramapp.navigation.Discover
import com.example.testing_apis.model.RemoteDataSource
import kotlinx.coroutines.launch

class SignupViewModel(
    private val navController: NavController,
    private val settingsRepository: SettingsRepository,
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
        var remoteDataSource = RemoteDataSource()

        if (username != "" && biography != "" && dob != "" && image != "") {
            viewModelScope.launch {

                //Chiamata di rete
                try {
                    var (userId, sessionId) = remoteDataSource.signUpUser()

                    //Fornisco il sessionId al remoteDataSource
                    remoteDataSource.provideSessionId(sessionId)

                    //Aggiorno l'utente creato con i dati inseriti dall'utente
                    remoteDataSource.updateUserDetails(
                        username = username,
                        bio = biography,
                        dob = dob
                    )

                    //Aggiungo l'immagine profilo
                    remoteDataSource.upadteUserImage(
                        base64 = image
                    )

                    settingsRepository.setLoggedUser(userId, sessionId)
                    navController.navigate(Discover)
                    snackBarHostState.showSnackbar("Registrazione Eseguita")

                } catch (error: APIException) {
                    //Cattura di qualsiasi errore di rete
                    error.message?.let { snackBarHostState.showSnackbar(it) }
                }
            }
        } else {
            viewModelScope.launch {
                snackBarHostState.showSnackbar("Compilare tutti i campi")
            }
        }
    }
}