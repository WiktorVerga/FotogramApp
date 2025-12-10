package com.example.fotogramapp.ui.components.post

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class PostListViewModel : ViewModel() {

    var firstVisibleItemIndex by mutableStateOf(0)
        private set

    var firstVisibleItemScrollOffset by mutableStateOf(0)
        private set

    fun saveListState(
        index: Int,
        offset: Int
    ) {
        firstVisibleItemIndex = index
        firstVisibleItemScrollOffset = offset
    }
}