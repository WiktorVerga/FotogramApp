package com.example.fotogramapp.ui.components.mappin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fotogramapp.ui.components.images.PrimaryImage

@Composable
fun MapPin(modifier: Modifier = Modifier, image: String, text: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(width = 48.dp, height = 70.dp)
                .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
                .clip(shape = RoundedCornerShape(10.dp))
        ) {
            // == Post Image ==
            PrimaryImage(
                image64 = image,
                isPfp = false
            )
        }

        // == Post Creator Username ==
        Text(
            text,
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .size(6.dp)
                .background(Color.Black, shape = CircleShape)
                .border(width = 1.dp, color = Color.White, shape = CircleShape)
        )
    }
}

