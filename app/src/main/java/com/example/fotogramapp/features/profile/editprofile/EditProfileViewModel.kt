package com.example.fotogramapp.features.profile.editprofile

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.repository.UserRepository
import kotlinx.coroutines.launch
import okio.IOException

class EditProfileViewModel(
    private val snackBarHostState: SnackbarHostState,
    private val userRepo: UserRepository,
    private val navController: NavController
) : ViewModel() {
    var username by mutableStateOf<String?>(null)
        private set

    var biography by mutableStateOf<String?>(null)
        private set

    var dob by mutableStateOf<String?>(null)
        private set

    var image by mutableStateOf<String?>(null)
        private set

    // == Handle Functions ==
    val handleUsername: (String) -> Unit = { newUsername ->
        username = newUsername
    }

    val handleBiography: (String) -> Unit = { newBiography ->
        biography = newBiography
    }

    val handleDob: (String) -> Unit = { newDob ->
        dob = newDob
    }

    val handleImage: (String) -> Unit = { newImage ->
        image = newImage
    }

    val handleSave: () -> Unit = {

        viewModelScope.launch {

            //Salvataggio dei nuovi dati
            try {

                userRepo.updateUserDetails(username, biography, dob, image)

                snackBarHostState.showSnackbar("Profile Saved")
                navController.popBackStack()
            } catch (error: APIException) {
                //Cattura di qualsiasi errore della userSignup
                error.message?.let { snackBarHostState.showSnackbar(it) }
            } catch (e: IOException) {
                snackBarHostState.showSnackbar("No Internet Connection")
            }
        }
    }
}
