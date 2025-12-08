package com.example.fotogramapp.ui.components.post.postcard

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.Post

class PostCardViewModel: ViewModel() {
    private val postRepo = PostRepository()
    private val userRepo = UserRepository()


    // == Post Data ==
    var creatorUsername by mutableStateOf("")
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

    // == Methods ==

    fun loadPostData(post: Post) {
        val user = userRepo.getUser(post.authorId)

        creatorUsername = user.username
        message = post.message
        image = post.image

        hasLocation = post.location != null

        isCurrentUser = post.authorId == user.id
    }


}