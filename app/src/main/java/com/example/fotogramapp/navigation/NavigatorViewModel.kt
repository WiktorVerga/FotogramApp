package com.example.fotogramapp.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fotogramapp.data.repository.SettingsRepository
import kotlinx.coroutines.launch

class NavigatorViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {

    var isFirstAccess by mutableStateOf(false)
        private set


    fun checkFirstAccess() {
        viewModelScope.launch {
            isFirstAccess = settingsRepository.isFirstAccess()
        }
    }
}

inline fun <reified T : Any> androidx.navigation.NavDestination?.isRoute(): Boolean {
    if (this == null) return false

    val baseRouteName = T::class.qualifiedName ?: return false

    //Prendo la route della destinazione corrente e tengo solo la parte prima
    val currentBaseRoute = this.route?.substringBefore('/')?.substringBefore('{')

    //Confronto le due stringhe base
    return currentBaseRoute.equals(baseRouteName, ignoreCase = true)
}