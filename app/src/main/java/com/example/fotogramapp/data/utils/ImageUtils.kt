package com.example.fotogramapp.data.utils

import android.graphics.Bitmap
import android.util.Base64
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


 fun String.toBitmap(): Bitmap? {
    if (this == "") return null

    return try {
        val decodedBytes = Base64.decode(this, Base64.NO_WRAP)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        null
    }
}

suspend fun Bitmap?.toBase64(): String? = withContext(Dispatchers.IO) {
    if (this@toBase64 == null) return@withContext null

    try {
        Log.d("ImageInputViewModel", "Sto prendendo l'immagine da Picker")
        val outputStream = ByteArrayOutputStream()
        this@toBase64.compress(Bitmap.CompressFormat.PNG, 70, outputStream)
        val byteArray = outputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.NO_WRAP)
    } catch (e: Exception) {
        null
    }
}