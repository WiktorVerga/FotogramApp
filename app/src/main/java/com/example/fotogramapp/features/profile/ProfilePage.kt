package com.example.fotogramapp.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.features.profile.components.BentoInformation
import com.example.fotogramapp.ui.components.buttons.PrimaryButton
import com.example.fotogramapp.ui.components.images.PrimaryImage
import com.example.fotogramapp.ui.components.post.postcard.PostCard
import com.example.fotogramapp.ui.theme.FotogramTheme

@Composable
fun ProfilePage(modifier: Modifier = Modifier, navController: NavHostController, userId: Int) {

    val viewModel: ProfileViewModel = viewModel()

    LaunchedEffect(Unit) {

        viewModel.loadUserData(userId)
    }

    LazyColumn (
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // == Profile Header ==
        item {
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
                    PrimaryImage(image64 = viewModel.profilePicture, isPfp = true)
                }
                Column() {
                    Text(
                        text = if (viewModel.isCurrentUser) "Hello!" else "This is:",
                        style = MaterialTheme.typography.labelLarge,
                    )
                    Text(
                        text = viewModel.username, style = MaterialTheme.typography.headlineMedium,
                    )
                }
            }

            // == Bento Information ==
            BentoInformation(
                modifier = Modifier
                    .padding(vertical = 20.dp),
                biograpy = viewModel.biography,
                followers = viewModel.followersCount,
                following = viewModel.followingCount,
                dob = viewModel.dob,
                postCounter = viewModel.postCount,
            )

            // == Follow / Edit Button ==
            PrimaryButton(text = "Edit Profile", onClick = {
                //TODO: aggiungi azione bottone e condizione per currentUser
            })

            // == Posts Title ==
            Text("Posts",
                modifier = Modifier
                    .padding(top = 50.dp, bottom = 20.dp)
                    .fillMaxWidth(),
                style = MaterialTheme.typography.headlineLarge
            )
        }


        // == Posts ==
        itemsIndexed(viewModel.posts) { index, post ->
            PostCard(key = index.toString(), post = post, navController = navController)
        }

        // == End Spacer ==
        item {
            Box(modifier = Modifier.size(50.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfilePagePrev() {
    FotogramTheme() {
        ProfilePage(navController = rememberNavController(), userId = 1)
    }
}