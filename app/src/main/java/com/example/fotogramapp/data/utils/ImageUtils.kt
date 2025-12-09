package com.example.fotogramapp.data.utils

import android.graphics.Bitmap
import android.util.Base64
import android.graphics.BitmapFactory

object ImageUtils {
    fun base64ToBitmap(base64: String): Bitmap? {
        if (base64 == "") return null

        return try {
            val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            null
        }
    }
}