package com.example.fotogramapp.ui.components.images.zoomoverlays

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import com.example.fotogramapp.ui.theme.CustomIcons
import com.example.fotogramapp.ui.theme.shimmerEffect
import kotlin.math.roundToInt

@Composable
fun PrimaryImageZoom(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    image: Bitmap?,
    onDismiss: () -> Unit,
) {

    var offsetX by mutableStateOf(0f)
    var offsetY by mutableStateOf(0f)


    Box(modifier = modifier.fillMaxSize()) {
        if (show) {
            Dialog(
                onDismissRequest = onDismiss,
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .blur(20.dp),
                    )

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    val maxX = 200f
                                    val maxY = 200f

                                    offsetX = (offsetX + dragAmount.x).coerceIn(-maxX, maxX)
                                    offsetY = (offsetY + dragAmount.y).coerceIn(-maxY, maxY)

                                    //Controllo che lo swipe sia principalmente orizzontale
                                    if (offsetX == -maxX || offsetX == maxX || offsetY == -maxY || offsetY == maxY) {
                                        onDismiss()

                                        change.consume()
                                    }
                                }
                            }
                    ) {
                        if (image != null)
                            Image(
                                bitmap = image.asImageBitmap(),
                                contentDescription = "Fullscreen Image"
                            )
                        else {
                            SubcomposeAsyncImage(
                                modifier = modifier
                                    .fillMaxSize()
                                    .zIndex(1f),
                                model = "https://placehold.co/330x330/png?text=NO+IMAGE",
                                contentDescription = "Image Placeholder",
                                error = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
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
                                contentScale = ContentScale.Fit,
                            )
//                        Box(
//                            modifier = Modifier
//                                .fillMaxSize(0.85f)
//                                .background(MaterialTheme.colorScheme.tertiaryContainer)
//                                .clickable(enabled = false) {  },
//                            contentAlignment = Alignment.Center
//                        ) {
//                            Text(
//                                text = "Image Failed to Load",
//                                style = MaterialTheme.typography.labelLarge
//                            )
//                        }
                        }
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ImageZoomPrev() {
    PrimaryImageZoom(
        image = null,
        onDismiss = {  }
    )

}