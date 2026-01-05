package com.example.fotogramapp.ui.components.inputs.imageinput

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.scale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fotogramapp.data.utils.toBase64
import com.example.fotogramapp.data.utils.toBitmap
import kotlinx.coroutines.launch
import okio.utf8Size

class ImageInputViewModel : ViewModel() {

    // == State ==
    var loading by mutableStateOf(false)
        private set

    var imageUri by mutableStateOf<Uri?>(null)
        private set
    var bitmap by mutableStateOf<Bitmap?>(null)
        private set

    var hasError by mutableStateOf(false)
        private set




    // == Methods ==
    fun getImageFromPicker(uri: Uri?, context: Context, getBase64Image: (String) -> Unit) {
        viewModelScope.launch {
            loading = true

            imageUri = uri

            imageUri?.let {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmap = ImageDecoder.decodeBitmap(source)
            }


            val compressBitmap = bitmap
            compressBitmap?.let {
                val scale = minOf(300f / compressBitmap.width, 300f / compressBitmap.height)
                bitmap = compressBitmap.scale((compressBitmap.width * scale).toInt(), (compressBitmap.height * scale).toInt())
            }


            val base64Image = bitmap.toBase64()

            base64Image?.let {
                hasError = !imageSizeIsValid(base64Image)
                if (!hasError) {
                    getBase64Image(base64Image)
                } else {
                    removeInsert()
                }
            }

            loading = false
        }
    }

    fun imageSizeIsValid(base64Image: String): Boolean {
        Log.d("ImageInputViewModel", "Sto controllando la dimensione dell'immagine")
        return base64Image.utf8Size() < 80000
    }

    fun removeInsert() {
        imageUri = null
        bitmap = null
    }

    fun setInitialImage(initialImage: String) {
        bitmap = initialImage.toBitmap()
    }
}