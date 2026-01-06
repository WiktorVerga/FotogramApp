package com.example.fotogramapp.ui.components.navbar

import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.LocalDataStore
import com.example.fotogramapp.app.LocalNavController
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.navigation.*
import com.example.fotogramapp.ui.theme.CustomIcons

@Composable
fun Navbar(modifier: Modifier = Modifier) {

    val settingsRepository = LocalDataStore.current
    val navController = LocalNavController.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val viewModel: NavbarViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                NavbarViewModel(settingsRepository, navController)
            }
        }
    )



    // == Navbar Logic ==
    if (!(currentDestination.isRoute<SignUp>() || currentDestination.isRoute<CreatePost>() || currentDestination.isRoute<EditProfile>())) {
        NavigationBar(
            modifier =
                modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
            windowInsets = NavigationBarDefaults.windowInsets,
            containerColor = MaterialTheme.colorScheme.secondary,
        ) {
            // == Discover ==
            NavigationBarItem(
                selected = currentDestination.isRoute<Discover>(),
                onClick = {
                    navController.navigate(Discover)
                },
                icon = {
                    Icon(
                        painter = painterResource(CustomIcons.Discover),
                        contentDescription = "Discover"
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                )
            )

            // == Map ==
            NavigationBarItem(
                selected = currentDestination.isRoute<MapPage>(),
                onClick = {
                    navController.navigate(
                        MapPage(
                            startingLongitude = null,
                            startingLatitude = null,
                        )
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(CustomIcons.MapPin),
                        contentDescription = "MapPage"
                    )
                },
            )

            // == Profile ==
            NavigationBarItem(
                selected = currentDestination.isRoute<Profile>(),
                onClick = {
                    viewModel.handleProfileNav()
                },
                icon = {
                    Icon(
                        painter = painterResource(CustomIcons.Profile),
                        contentDescription = "Discover"
                    )
                },
            )
        }
    }
}