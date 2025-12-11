package com.example.fotogramapp.features.discover

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.domain.model.Post
import kotlinx.coroutines.launch

class DiscoverViewModel(
    private val database: AppDatabase,
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val postRepo = PostRepository(database, settingsRepository)
    private val userRepo = UserRepository(database, settingsRepository)
    private val startPagination: Int = 1
    private val endPagination: Int = 5

    var posts by mutableStateOf(listOf<Post>())
        private set

    fun loadPosts() {
        viewModelScope.launch {
            posts = postRepo.getPosts(startPagination..endPagination)
        }
    }
}