package com.example.fotogramapp.ui.components.post

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fotogramapp.ui.components.images.PrimaryImage
import com.example.fotogramapp.ui.components.post.postmenu.PostCardMenu
import com.example.fotogramapp.ui.theme.CustomIcons
import com.example.fotogramapp.ui.theme.shimmerEffect

@Composable
fun PostPlaceholderCard(modifier: Modifier = Modifier) {

    val shimmerColors = listOf(
        MaterialTheme.colorScheme.secondaryContainer.copy(0.8f),
        Color.White.copy(0.5f),
        MaterialTheme.colorScheme.secondaryContainer.copy(0.8f),
    )

    Card(
        modifier = modifier
            .size(width = 330.dp, height = 500.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Box(
            modifier = Modifier
        ) {
            Column(

            ) {
                // == User Header ==
                Row(
                    modifier = Modifier
                        .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 45.dp, height = 45.dp)
                                .clip(CircleShape)
                                .shimmerEffect(shimmerColors)
                        ) {}

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .clip(RoundedCornerShape(15.dp))
                                .shimmerEffect(shimmerColors)
                        ) {}
                    }
                }

                // == Post Image ==
                Box(
                    modifier = Modifier
                        .size(width = 330.dp, height = 330.dp)
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                        .shimmerEffect(shimmerColors)
                ) {}

                // == Post Message & Location ==
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .shimmerEffect(shimmerColors),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {}
            }
        }

    }

}