package com.example.fotogramapp.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fotogramapp.features.profile.components.BentoContainer
import com.example.fotogramapp.features.profile.components.BentoInformation
import com.example.fotogramapp.ui.components.buttons.PrimaryButton
import com.example.fotogramapp.ui.components.post.PostCard
import com.example.fotogramapp.ui.components.post.PostList
import com.example.fotogramapp.ui.theme.FotogramTheme
import com.mapbox.maps.extension.compose.style.layers.generated.ModelLayer

@Composable
fun ProfilePage(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {

        // == Profile Header ==
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
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
                    text = "Hello!",
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    "CreatorUsername", style = MaterialTheme.typography.headlineMedium,
                )
            }
        }

        // == Bento Information ==
        BentoInformation(
            modifier = Modifier
                .padding(vertical = 20.dp)
        )

        // == Follow / Edit Button ==
        PrimaryButton(text = "Edit Profile", onClick = {

        })

        // == Posts ==
        Text("Posts",
            modifier = Modifier
                .padding(top = 50.dp, bottom = 20.dp),
            style = MaterialTheme.typography.headlineLarge
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (i in 1..10) {
                PostCard()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePagePrev() {
    FotogramTheme() {
        ProfilePage()
    }
}