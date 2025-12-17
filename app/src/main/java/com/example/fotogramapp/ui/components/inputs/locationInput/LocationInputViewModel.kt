package com.example.fotogramapp.ui.components.inputs.locationInput

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fotogramapp.data.repository.MapRepository
import com.mapbox.geojson.Point
import kotlinx.coroutines.launch

class LocationInputViewModel(
    private val accessToken: String
) : ViewModel() {

    var location by mutableStateOf<Point?>(null)
        private set

    var address by mutableStateOf<String?>(null)
        private set

    var showLocationPicker by mutableStateOf(false)
        private set


    // == Methods ==
    fun showLocationPicker() {
        showLocationPicker = true
    }

    val handleDismiss: () -> Unit  = {
        showLocationPicker = false
    }

    val handleGetLocation: (Point) -> Unit = {
        location = it
    }

    fun locationToAddress(point: Point) {
        val mapRepo = MapRepository()
        viewModelScope.launch {
            address = mapRepo.getAddressFromPoint(point.latitude(), point.longitude(), accessToken)
        }
    }

}