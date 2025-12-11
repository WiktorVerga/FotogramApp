package com.example.fotogramapp.ui.components.post

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.navigation.Profile
import kotlinx.coroutines.launch

class PostCardViewModel(val navController: NavController, private val database: AppDatabase) : ViewModel() {
    private val postRepo = PostRepository()
    private val userRepo = UserRepository(database)

    // == Post Data ==
    var creatorUsername by mutableStateOf("")
        private set

    var creatorPicture by mutableStateOf<String?>("")
        private set

    private var creatorId by mutableStateOf(-1)
        private set
    var message by mutableStateOf("")
        private set

    var image by mutableStateOf("")
        private set

    var hasLocation by mutableStateOf(false)
        private set

    var isSuggested by mutableStateOf(false)
        private set

    var isCurrentUser by mutableStateOf(false)
        private set

    // == HandleFunctions ==

    val handleProfileOnClick = {
        navController.navigate(
            Profile(
                id = creatorId
            )
        )
    }


    // == Methods ==

    fun loadPostData(post: Post) {
        viewModelScope.launch {
            val user = userRepo.getUser(post.authorId)
            if (user != null) {
                //Creator Data
                creatorPicture = user.profilePicture
                creatorId = user.id
                creatorUsername = user.username
                isCurrentUser = post.authorId == 1 //Prendi id da DataStore

                //Post Data
                message = post.message
                image = post.image
                hasLocation = post.location != null
            }
        }
    }



}