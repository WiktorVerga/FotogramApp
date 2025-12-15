package com.example.fotogramapp.data.utils

import android.graphics.Bitmap
import android.util.Base64
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream


 fun String.toBitmap(): Bitmap? {
    if (this == "") return null

    return try {
        val decodedBytes = Base64.decode(this, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: Exception) {
        null
    }
}

fun Bitmap?.toBase64(): String? {
    if (this == null) return null

    return try {
        val outputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        null
    }
}