package com.example.fotogramapp.features.profile

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.Post

class ProfileViewModel: ViewModel() {
    private val userRepo = UserRepository()
    private val postRepo = PostRepository()

    // == User Data ==
    var username by mutableStateOf("")
        private set

    var biography by mutableStateOf("")
        private set

    var profilePicture: String by mutableStateOf("")
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

    // == Methods ==

    fun loadUserData(userId: Int) {
        val user = userRepo.getUser(userId)


        username = user.username
        biography = user.biography
        profilePicture = user.profilePicture
        followersCount = user.followersCount
        followingCount = user.followingCount
        dob = user.birthDate
        postCount = user.postCount
        posts = postRepo.getUserPosts(userId)
    }
}