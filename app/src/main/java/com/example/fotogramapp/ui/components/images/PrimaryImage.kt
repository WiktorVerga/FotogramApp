package com.example.fotogramapp.ui.components.images

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.fotogramapp.R
import com.example.fotogramapp.data.utils.ImageUtils
import com.example.fotogramapp.ui.components.images.zoomoverlays.PrimaryImageZoom
import com.example.fotogramapp.ui.theme.shimmerEffect

@Composable
fun PrimaryImage(modifier: Modifier = Modifier, image64: String = "", isPfp: Boolean = false) {

    val imageBitmap: Bitmap? = ImageUtils.base64ToBitmap(image64)
    PrimaryImage(modifier, imageBitmap, isPfp)
}

@Composable
fun PrimaryImage(modifier: Modifier = Modifier, imageBitmap: Bitmap?, isPfp: Boolean = false) {
    var showZoomImage by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clickable(enabled = !isPfp, onClick = {
                showZoomImage = true
            }),
        contentAlignment = Alignment.Center
    ) {
        if (imageBitmap != null) {
            if (isPfp) {
                //Show Image as Profile Picture
                Image(
                    bitmap = imageBitmap.asImageBitmap(),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                )
            } else {
                //Show normal Image, with 1:1 ration
                Image(
                    bitmap = imageBitmap.asImageBitmap(),
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                )
            }
        } else {
            if (isPfp) {
                //Placeholder for Profile Picture
                Image(
                    painter = painterResource(id = R.drawable.user_placeholder),
                    contentDescription = "Profile Picture Placeholder",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)

                )
            } else {
                //Placeholder for normal Image
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    model = "https://placehold.co/900x1600/png?text=NO+IMAGE",
                    contentDescription = "Image Placeholder",
                    error = {
                        Box(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.tertiaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Image Failed to Load",
                                style = MaterialTheme.typography.labelLarge
                            )
                        }
                    },
                    loading = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .shimmerEffect()
                        )
                    },
                    contentScale = ContentScale.Crop,
                )
            }
        }

        PrimaryImageZoom(
            show = showZoomImage,
            image = imageBitmap,
            onDismiss = {
                showZoomImage = false
            }
        )
    }
}