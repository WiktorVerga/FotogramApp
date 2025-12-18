package com.example.fotogramapp.ui.components.inputs.locationInput

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import com.example.fotogramapp.data.repository.LocationException
import com.example.fotogramapp.data.repository.MapRepository
import com.mapbox.common.location.Location
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapboxMap
import io.ktor.client.request.invoke
import io.ktor.http.invoke

@Composable
fun LocationPicker(
    onDismiss: () -> Unit,
    getLocation: (Point) -> Unit
) {

    val mapRepo = MapRepository()

    var hasPermission by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {isGranted ->
        hasPermission = isGranted
    }

    var startLocation = Point.fromLngLat(9.2319522, 45.4759249)

    var selectedLocation by remember { mutableStateOf<Point?>(null) }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(15.0)
            center(startLocation)
            pitch(0.0)
            bearing(0.0)
        }
    }

    LaunchedEffect(Unit) {
        hasPermission = mapRepo.checkLocationPermission(context)
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(hasPermission) {
        try {
            startLocation = mapRepo.getCurrentLocation(context)
            mapViewportState.setCameraOptions {
                center(startLocation)
            }
        } catch (e: LocationException) {
            Log.d("LocationException", "LocationPicker: ${e.message}")
        }
    }

    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            ) {

                MapboxMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    mapViewportState = mapViewportState
                )

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Center marker",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp)
                        .offset(y = (-24).dp),
                    tint = Color.Red
                )

                Button(
                    onClick = {
                        selectedLocation = mapViewportState.cameraState?.center
                        selectedLocation?.let {
                            getLocation(selectedLocation!!)
                            onDismiss()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save location"
                    )
                }
            }
        }
    }

}
