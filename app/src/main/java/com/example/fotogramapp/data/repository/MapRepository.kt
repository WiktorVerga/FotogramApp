package com.example.fotogramapp.data.repository

import android.util.Log
import com.example.fotogramapp.data.remote.APIException
import com.example.testing_apis.model.RemoteDataSource

class MapRepository {

    suspend fun getAddressFromPoint(lat: Double, lon: Double, accessToken: String): String? {
        try {
            val remoteDataSource = RemoteDataSource()
            val address = remoteDataSource.getAddress(lon, lat, accessToken)

            return address
        } catch (e: APIException) {
            Log.d("APIException", "getAddressFromPoint: ${e.message}")
        }

        return null
    }
}