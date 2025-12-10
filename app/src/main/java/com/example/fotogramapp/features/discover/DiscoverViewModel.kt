package com.example.fotogramapp.features.discover

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.Post

class DiscoverViewModel: ViewModel() {
    private val postRepo = PostRepository()
    private val userRepo = UserRepository()
    private val startPagination: Int = 1
    private val endPagination: Int = 4

    var posts by mutableStateOf(listOf<Post>())
        private set

    fun loadPosts() {
        posts = postRepo.getPosts(startPagination..endPagination)
    }
}