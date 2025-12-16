package com.example.fotogramapp.features.profile

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.remote.APIException
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.Post
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val settingsRepository: SettingsRepository,
    private val database: AppDatabase
) : ViewModel() {
    private val userRepo = UserRepository(database, settingsRepository)
    private val postRepo = PostRepository(database, settingsRepository)

    // == State ==
    var loading by mutableStateOf(true)
        private set


    // == User Data ==
    var id by mutableStateOf<Int?>(null)
    var username by mutableStateOf("")
        private set

    var biography by mutableStateOf("")
        private set

    var profilePicture: String? by mutableStateOf("")
        private set

    var followersCount by mutableStateOf(0)
        private set

    var followingCount by mutableStateOf(0)
        private set

    var postCount by mutableStateOf(0)
        private set

    var posts by mutableStateOf(listOf<Post>())
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
            //TODO: chiamata di rete per fare unfollow, gestita da model?
            Log.d("ProfileViewModel", "Sto togliendo il Follow")
            viewModelScope.launch {
                id?.let {
                    userRepo.unFollowUser(it)
                    loadUserData(it)
                }

            }
        } else {
            //TODO: chiamata di rete per fare follow, gestita da model?
            Log.d("ProfileViewModel", "Sto mettendo il Follow")
            viewModelScope.launch {
                id?.let {
                    userRepo.followUser(it)
                    loadUserData(it)
                }

            }
        }

        isFollowing = !isFollowing
    }



    val handleEditProfile = {

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
                profilePicture = user.profilePicture
                followersCount = user.followersCount
                followingCount = user.followingCount
                dob = user.dateOfBirth
                postCount = user.postsCount


                isCurrentUser = userRepo.isLoggedUser(userId)
                Log.d("ProfileViewModel", "isCurrentUser: $isCurrentUser")

                if (!isCurrentUser) {
                    Log.d("ProfileViewModel", "isFollowing: ${user.isYourFollowing}")
                    isFollowing = user.isYourFollowing
                }
            } catch (error: APIException) {
                error.message?.let { Log.d("ProfileViewModel", it) }
            }
            loading = false
        }
    }
}