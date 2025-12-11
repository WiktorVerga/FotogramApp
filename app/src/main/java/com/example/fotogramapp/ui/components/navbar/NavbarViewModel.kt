package com.example.fotogramapp.ui.components.navbar

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fotogramapp.data.repository.SettingsRepository
import kotlinx.coroutines.launch

class NavbarViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    // == State ==
    var loggedUserId by mutableStateOf<Int?>(null)
        private set

    // == Methods ==

    fun loadUserId() {
        viewModelScope.launch {
            loggedUserId = settingsRepository.getLoggedUser()
        }
    }

}