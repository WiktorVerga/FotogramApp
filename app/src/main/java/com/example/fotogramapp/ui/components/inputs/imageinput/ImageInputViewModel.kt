package com.example.fotogramapp.ui.components.inputs.imageinput

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel

class ImageInputViewModel : ViewModel() {
    var imageUri by mutableStateOf<Uri?>(null)
    var bitmap by mutableStateOf<Bitmap?>(null)

}