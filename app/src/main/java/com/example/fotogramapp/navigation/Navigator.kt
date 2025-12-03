package com.example.fotogramapp.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigator(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Discover,
    ) {
        composable<SignUp> {
            //TODO: Signup Page
        }
        composable<Discover> {
            //TODO: Discover Page
            Text("Discover")
        }
        composable<Profile> {
            //TODO: Profile Page
            Text("Profile")
        }
        composable<CreatePost> {
            //TODO: Create Post Page
        }
        composable<Map> {
            //TODO: Map Page
            Text("Map")
        }
    }

}
