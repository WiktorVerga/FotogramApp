package com.example.fotogramapp.ui.components.inputs.imageinput

import android.R.attr.bitmap
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fotogramapp.ui.components.images.PrimaryImage
import com.example.fotogramapp.ui.theme.CustomIcons

@Composable
fun ImageInput(modifier: Modifier = Modifier, id: String, title: String = "Title", getBase64Image: (String) -> Unit, isPfp: Boolean = false) {
    
    val viewModel: ImageInputViewModel = viewModel(key = id)
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(contract =
        ActivityResultContracts.GetContent()) { uri: Uri? ->

        viewModel.getImageFromPicker(uri, context, getBase64Image)

    }
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (viewModel.hasError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(30.dp)
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            Text(title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )

            TextButton (
                onClick = {
                    viewModel.removeInsert()
                    launcher.launch("image/*")
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),

            ) {
                Text(
                    text = "Choose Image",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            if (viewModel.hasError)
                Text("Immagine troppo grande", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)


            val bitmapPicked = viewModel.bitmap
            if (bitmapPicked != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        PrimaryImage(
                            imageBitmap = bitmapPicked,
                            isPfp = isPfp
                        )
                    }

                    TextButton(
                        onClick = {
                            viewModel.removeInsert()
                        },
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(25.dp),
                            painter = painterResource(CustomIcons.Exit),
                            contentDescription = "Exit Icon",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}