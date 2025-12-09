package com.example.fotogramapp.ui.components.images.profilepicture

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fotogramapp.R
import com.example.fotogramapp.data.utils.ImageUtils

@Composable
fun ProfilePicture(modifier: Modifier = Modifier, image64: String = "") {

    val imageBitmap: Bitmap? = ImageUtils.base64ToBitmap(image64)

    Box(
        modifier = modifier
            .clip(CircleShape),
    ) {
        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = "User Profile Picture"
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.user_placeholder),
                contentDescription = "User Placeholder",
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .size(80.dp)
            )
        }
    }

}