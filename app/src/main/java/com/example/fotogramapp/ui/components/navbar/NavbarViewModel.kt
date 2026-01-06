package com.example.fotogramapp.ui.components.navbar

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.navigation.Profile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NavbarViewModel(
    private val settingsRepository: SettingsRepository,
    private val navController: NavController
) : ViewModel() {

    // == Handle Functions ==
    fun handleProfileNav() {
        viewModelScope.launch {
            val loggedUserId = settingsRepository.getLoggedUserId()
            navController.navigate(
                Profile(
                    id = loggedUserId
                )
            )
        }
    }

}