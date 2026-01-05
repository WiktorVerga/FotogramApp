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
import com.example.fotogramapp.features.map.MapPage
import com.example.fotogramapp.features.profile.ProfilePage
import com.example.fotogramapp.features.profile.editprofile.EditProfilePage
import com.example.fotogramapp.features.signup.SignupPage
import com.mapbox.geojson.Point

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

    // == Launched Effects ==
    LaunchedEffect(Unit) {
        //Check if it's First Access
        viewModel.checkFirstAccess()
    }

    // == Navigation Logic ==
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
                ProfilePage(userId = args.id)
            }

        }
        composable<EditProfile> {
            val args = it.toRoute<EditProfile>()

            EditProfilePage(
                username = args.username,
                biography = args.biography,
                dob = args.dob,
                image = args.image
            )
        }
        composable<CreatePost> {
            CreatePostPage()
        }
        composable<MapPage> {
            val args = it.toRoute<MapPage>()
            if (args.startingLatitude == null || args.startingLongitude == null) {
                MapPage()
            } else {
                MapPage(postLocation = Point.fromLngLat(args.startingLongitude, args.startingLatitude))
            }

        }
    }
}


