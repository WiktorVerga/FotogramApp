package com.example.fotogramapp.ui.components.inputs.locationInput

import com.example.fotogramapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fotogramapp.ui.components.images.PrimaryImage
import com.example.fotogramapp.ui.theme.CustomIcons
import com.example.fotogramapp.ui.theme.shimmerEffect
import com.mapbox.geojson.Point

@Composable
fun LocationInput(
    modifier: Modifier = Modifier,
    id: String,
    title: String = "Title",
    getSafeLocation: (Point) -> Unit
) {

    val context = LocalContext.current

    val accessToken = context.getString(R.string.mapbox_access_token)
    val viewModel: LocationInputViewModel = viewModel(
        key = id,
        factory = viewModelFactory {
            initializer {
                LocationInputViewModel(accessToken)
            }
        }
    )

    LaunchedEffect(viewModel.location) {
        val safeLocation = viewModel.location
        if (safeLocation != null) {
            getSafeLocation(safeLocation)
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(30.dp)
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            Text(
                title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )

            TextButton(
                onClick = {
                    //Open Dialog with map
                    viewModel.showLocationPicker()
                },
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),

                ) {
                Text(
                    text = "Choose Location",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            val addressPicked: String? = viewModel.address

            if (addressPicked != null) {
                Text(
                    text = addressPicked,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }
    }

    if (viewModel.showLocationPicker) {
        LocationPicker(
            onDismiss = { viewModel.handleDismiss() },
            getLocation = viewModel.handleGetLocation
        )
    }
}