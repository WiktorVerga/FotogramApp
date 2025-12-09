package com.example.fotogramapp.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fotogramapp.features.createpost.CreatePostPage
import com.example.fotogramapp.features.discover.DiscoverPage
import com.example.fotogramapp.features.profile.ProfilePage
import com.example.fotogramapp.features.signup.SignupPage

@Composable
fun Navigator(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = SignUp,
    ) {
        composable<SignUp> {
            SignupPage(navController = navController)
        }
        composable<Discover> {
            DiscoverPage(navController = navController)
        }
        composable<Profile> {
            val args = it.toRoute<Profile>()
            //TODO: Profile Page
            ProfilePage(navController = navController, userId = args.id)
        }
        composable<CreatePost> {
            CreatePostPage(navController = navController)
        }
        composable<MapPage> {
            //TODO: MapPage Page
            Text("MapPage")
        }
    }

}

inline fun <reified T : Any> androidx.navigation.NavDestination?.isRoute(): Boolean {
    if (this == null) return false

    val baseRouteName = T::class.qualifiedName ?: return false

    //Prendo la route della destinazione corrente e tengo solo la parte prima
    val currentBaseRoute = this.route?.substringBefore('/')?.substringBefore('{')

    //Confronto le due stringhe base
    return currentBaseRoute.equals(baseRouteName, ignoreCase = true)
}
