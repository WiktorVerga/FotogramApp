package com.example.fotogramapp.ui.components.images.zoomoverlays

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.SubcomposeAsyncImage
import com.example.fotogramapp.R
import com.example.fotogramapp.ui.theme.shimmerEffect
import kotlin.math.roundToInt

@SuppressLint("UnrememberedMutableState")
@Composable
fun PrimaryImageZoom(
    modifier: Modifier = Modifier,
    show: Boolean = false,
    image: Bitmap?,
    onDismiss: () -> Unit
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
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.5f))
                            .blur(20.dp)
                            .clickable(onClick = {
                                onDismiss()
                            }),
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
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (image != null)
                            Image(
                                bitmap = image.asImageBitmap(),
                                contentDescription = "Fullscreen Image"
                            )
                        else {
                            //Placeholder for normal Image
                            SubcomposeAsyncImage(
                                modifier = modifier
                                    .fillMaxWidth(),
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
                                contentScale = ContentScale.Fit,
                            )
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
        onDismiss = {  },
    )

}