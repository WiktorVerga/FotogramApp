package com.example.fotogramapp.features.profile

import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.domain.model.User
import com.example.fotogramapp.navigation.EditProfile
import kotlinx.coroutines.launch
import okio.IOException

class ProfileViewModel(
    private val userRepo: UserRepository,
    private val postRepo: PostRepository,
    private val navController: NavController,
    private val snackbarHostState: SnackbarHostState
) : ViewModel() {

    // == State ==
    var loading by mutableStateOf(true)
        private set


    // == User Data ==

    var id by mutableStateOf(0)
        private set

    var username by mutableStateOf("")
        private set

    var biography by mutableStateOf("")
        private set

    var profilePicture by mutableStateOf("")
        private set

    var followersCount by mutableStateOf(0)
        private set

    var followingCount by mutableStateOf(0)
        private set

    var postCount by mutableStateOf(0)
        private set

    var posts by mutableStateOf(listOf<Int>())
        private set

    var dob by mutableStateOf("")
        private set

    var isCurrentUser by mutableStateOf(true)
        private set

    var isFollowing by mutableStateOf(false)
        private set

    // == Handle Functions ==
    val handleFollowToggle = {


        if (isFollowing) {
            //TODO: chiamata di rete per fare unfollow, gestita da model
            Log.d("ProfileViewModel", "Sto togliendo il Follow")
            viewModelScope.launch {

                userRepo.unFollowUser(id)
                loadUserData(id)

            }
        } else {
            //TODO: chiamata di rete per fare follow, gestita da userRepository
            Log.d("ProfileViewModel", "Sto mettendo il Follow")
            viewModelScope.launch {
                userRepo.followUser(id)
                loadUserData(id)
            }
        }

        isFollowing = !isFollowing
    }



    val handleEditProfile: () -> Unit = {
        navController.navigate(EditProfile(
            username = username,
            biography = biography,
            dob = dob,
            image = profilePicture
        ))
    }


    // == Methods ==
    fun loadUserData(userId: Int) {
        Log.d("ProfileViewModel", "Sto caricando i dati dell'utente: ${userId}")

        viewModelScope.launch {
            loading = true
            try {
                val user = userRepo.getUser(userId)

                id = user.id
                username = user.username
                biography = user.bio
                profilePicture = user.profilePicture ?: ""
                followersCount = user.followersCount
                followingCount = user.followingCount
                dob = user.dateOfBirth
                postCount = user.postsCount

                isCurrentUser = userRepo.isLoggedUser(userId)

                if (!isCurrentUser) {
                    isFollowing = user.isYourFollowing
                }

                loadPosts(user.id)

                loading = false
            } catch (error: APIException) {
                error.message?.let { Log.d("ProfileViewModel", it) }
                snackbarHostState.showSnackbar("Network Error")
            } catch (e: IOException) {
                snackbarHostState.showSnackbar("No Internet Connection", duration = SnackbarDuration.Long)
                //Riprovo la chiamata
                loadUserData(userId)
            }
        }
    }

    fun loadPosts(userId: Int) {
        viewModelScope.launch {
            posts += postRepo.getAuthorPosts(userId)
        }
    }
}