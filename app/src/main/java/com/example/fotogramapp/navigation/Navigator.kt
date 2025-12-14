package com.example.fotogramapp.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fotogramapp.LocalDataStore
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.features.createpost.CreatePostPage
import com.example.fotogramapp.features.discover.DiscoverPage
import com.example.fotogramapp.features.profile.ProfilePage
import com.example.fotogramapp.features.signup.SignupPage

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("LocalNavController not provided")
}
@Composable
fun Navigator(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {

    val settingsRepository = LocalDataStore.current
    val viewModel: NavigatorViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                NavigatorViewModel(settingsRepository)
            }
        }
    )

    LaunchedEffect(Unit) {
        //Check if it's First Access
        viewModel.checkFirstAccess()
    }

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = if (viewModel.isFirstAccess) SignUp else Discover,
        ) {
            composable<SignUp> {
                SignupPage()
            }
            composable<Discover> {
                DiscoverPage()
            }
            composable<Profile> {
                val args = it.toRoute<Profile>()

                args.id?.let {
                    ProfilePage(navController = navController, userId = args.id)
                }

            }
            composable<CreatePost> {
                CreatePostPage()
            }
            composable<MapPage> {
                //TODO: MapPage Page
                Text("MapPage")
            }
        }
    }
}


