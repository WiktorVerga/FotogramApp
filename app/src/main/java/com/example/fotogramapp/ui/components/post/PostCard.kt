

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fotogramapp.LocalAppDatabase
import com.example.fotogramapp.LocalDataStore
import com.example.fotogramapp.LocalPostRepository
import com.example.fotogramapp.LocalUserRepository
import com.example.fotogramapp.app.LocalNavController
import com.example.fotogramapp.app.LocalSnackbar
import com.example.fotogramapp.domain.model.Post
import com.example.fotogramapp.ui.components.images.PrimaryImage
import com.example.fotogramapp.ui.components.post.PostCardViewModel
import com.example.fotogramapp.ui.theme.CustomIcons

@Composable
fun PostCard(modifier: Modifier = Modifier, key: String? = null, postId: Int) {
    val navController = LocalNavController.current
    val userRepo = LocalUserRepository.current
    val postRepo = LocalPostRepository.current
    val snackbarHostState = LocalSnackbar.current

    val viewModel: PostCardViewModel = viewModel(
        key = key,
        factory = viewModelFactory {
            initializer {
                PostCardViewModel(
                    navController = navController,
                    userRepo = userRepo,
                    postRepo = postRepo,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    )

    LaunchedEffect(Unit) {
        viewModel.loadPostData(postId)
    }

    Card(
        modifier = modifier
            .size(width = 330.dp, height = 500.dp),
        shape = RoundedCornerShape(30.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
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
                    modifier = Modifier
                        .clickable(onClick = viewModel.handleProfileOnClick)
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
                        PrimaryImage(image64 = viewModel.creatorPicture, isPfp = true)
                    }
                    Column {
                        Text(
                            text = viewModel.creatorUsername,
                            style = MaterialTheme.typography.headlineSmall,
                            fontSize = 20.sp
                        )
                        if (!viewModel.isCurrentUser) {
                            Text(
                                if (viewModel.isSuggested) "Suggested" else "You Follow",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
                if (viewModel.isSuggested) {
                    Icon(
                        modifier = Modifier
                            .size(45.dp),
                        painter = painterResource(CustomIcons.SuggestedUser),
                        contentDescription = "Suggested User Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // == Post Image ==
            Box(
                modifier = Modifier
                    .size(width = 330.dp, height = 330.dp)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
            ) {
                PrimaryImage(image64 = viewModel.image)
            }

            // == Post Message & Location ==
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 15.dp, end = 15.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(if (viewModel.hasLocation) 4 / 5f else 1f),
                    text = viewModel.message,
                    style = MaterialTheme.typography.bodyMedium,
                )
                if (viewModel.hasLocation) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        painter = painterResource(CustomIcons.MapPin),
                        contentDescription = "Map Location Icon",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}