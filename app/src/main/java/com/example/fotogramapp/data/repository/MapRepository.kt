package com.example.fotogramapp.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.fotogramapp.data.remote.APIException
import com.example.testing_apis.model.RemoteDataSource
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.mapbox.geojson.Point
import kotlinx.coroutines.tasks.await

class MapRepository {

    suspend fun getAddressFromPoint(lat: Double, lon: Double, accessToken: String): String? {
        try {
            val remoteDataSource = RemoteDataSource()
            val address = remoteDataSource.fetchAddress(lon, lat, accessToken)

            return address
        } catch (e: APIException) {
            Log.d("APIException", "getAddressFromPoint: ${e.message}")
        }

        return null
    }

    fun checkLocationPermission(context: Context) : Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
    }

    suspend fun getCurrentLocation(context: Context): Point {
        var calculatedLocation: Point
        val hasPermission = checkLocationPermission(context)

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        if (hasPermission) {
            val task = fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
            val location = task.await()
            calculatedLocation = Point.fromLngLat(location.longitude, location.latitude)
        } else {
            throw LocationException("Location permission not granted")
        }

        return calculatedLocation
    }
}

class LocationException(message: String) : Exception(message)

// Type for Map Marker
data class PostLocation(
    val coordinate: Point,
    val name: String,
    val image: String
)