package com.example.fotogramapp.ui.components.post

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fotogramapp.ui.theme.FotogramTheme
import com.example.fotogramapp.ui.theme.Icons

@Composable
fun PostCard(modifier: Modifier = Modifier) {
    var isSuggested by remember { mutableStateOf(true) }
    var hasLocation by remember { mutableStateOf(true) }


    Card(
        modifier = modifier
            .size(width = 330.dp, height = 500.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    )  {
        Column(

        ) {

            Row(
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 45.dp, height = 45.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape
                            )
                            .background(MaterialTheme.colorScheme.tertiaryContainer, CircleShape)

                    ) {
                        //TODO: Profile Image
                    }
                    Column() {
                        Text(
                            "CreatorUsername", style = MaterialTheme.typography.headlineSmall,
                            fontSize = 20.sp
                        )
                        Text(
                            if (isSuggested) "Suggested" else "You Follow",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                if (isSuggested) {
                    Icon(
                        modifier = Modifier
                            .size(45.dp),
                        painter = painterResource(Icons.SuggestedUser),
                        contentDescription = "Suggested User Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(width = 330.dp, height = 330.dp)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
            ) {
                //TODO: Post Image
            }

            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(if (hasLocation) 4 / 5f else 1f),
                    text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt.",
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (hasLocation) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        painter = painterResource(Icons.MapPin),
                        contentDescription = "Map Location Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PostCardPrev() {
    FotogramTheme() {
        PostCard()
    }

}