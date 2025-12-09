package com.example.fotogramapp.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
            SignupPage()
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
            //TODO: Create Post Page
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

    // 2. Prendiamo la 'route' della destinazione corrente e teniamo solo la parte prima
    //    di eventuali parametri (che iniziano con '?' o '{').
    val currentBaseRoute = this.route?.substringBefore('/')?.substringBefore('{')
    Log.d("TopBar", "${currentBaseRoute}")
    // 3. Confrontiamo le due stringhe base.
    return currentBaseRoute.equals(baseRouteName, ignoreCase = true)
}
