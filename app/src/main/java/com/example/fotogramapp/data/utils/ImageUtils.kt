package com.example.fotogramapp.data.utils

import android.graphics.Bitmap
import android.util.Base64
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream


//Extension of String Methods
fun String.toBitmap(): Bitmap? {
    if (this == "") return null

    return try {
        val decodedBytes = Base64.decode(this, Base64.NO_WRAP)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        null
    }
}

//Extension of Bitmap Methods
//Long IO operation -> used withContext(Dispatchers.IO)
suspend fun Bitmap?.toBase64(): String? = withContext(Dispatchers.IO) {
    if (this@toBase64 == null) return@withContext null

    try {
        val outputStream = ByteArrayOutputStream()

        //Image Compression to 70%
        this@toBase64.compress(Bitmap.CompressFormat.PNG, 70, outputStream)
        val byteArray = outputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.NO_WRAP)
    } catch (e: Exception) {
        null
    }
}

fun resizeBitmap(
    bitmap: Bitmap,
    width: Int,
    height: Int
): Bitmap {
    return Bitmap.createScaledBitmap(bitmap, width, height, true)
}