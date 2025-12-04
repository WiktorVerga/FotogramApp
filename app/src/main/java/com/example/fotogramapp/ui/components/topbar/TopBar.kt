package com.example.fotogramapp.ui.components.topbar

import android.util.Log
import androidx.compose.material3.*
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.enterAlwaysScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.navigation.CreatePost
import com.example.fotogramapp.navigation.Discover
import com.example.fotogramapp.navigation.MapPage
import com.example.fotogramapp.navigation.Profile
import com.example.fotogramapp.navigation.SignUp
import com.example.fotogramapp.navigation.isRoute
import com.example.fotogramapp.ui.theme.Icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentDestination = navBackStackEntry?.destination

    LaunchedEffect(navBackStackEntry) {
        currentDestination = navBackStackEntry?.destination
    }

    val screenTitle = when {
        currentDestination.isRoute<Discover>() -> "Fotogram"
        currentDestination.isRoute<Profile>() -> "Profile"
        currentDestination.isRoute<MapPage>() -> "Posts Map"
        currentDestination.isRoute<CreatePost>() -> "Create Post"
        currentDestination.isRoute<SignUp>() -> "Create Account"
        else -> "Page Not Found"
    }

    if (currentDestination.isRoute<Discover>()) {
        TopAppBar(
            title = {
                Text(screenTitle, style = MaterialTheme.typography.headlineMedium)
            },
            actions = {
                IconButton(
                    onClick = {
                        navController.navigate(CreatePost)
                    }
                ) {
                    Icon(
                        painter = painterResource(Icons.Add),
                        contentDescription = "Create new Post",
                    )
                }
            },
            scrollBehavior = enterAlwaysScrollBehavior(),
            windowInsets = TopAppBarDefaults.windowInsets,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background
            )
        )
    } else {
        CenterAlignedTopAppBar(
            title = {
                Text(screenTitle, style = MaterialTheme.typography.headlineMedium)
            },
            navigationIcon = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(Icons.Back),
                        contentDescription = "go back",
                    )
                }
            },
            scrollBehavior = enterAlwaysScrollBehavior(),
            windowInsets = TopAppBarDefaults.windowInsets,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                scrolledContainerColor = MaterialTheme.colorScheme.background
            )
        )
    }
}

@Preview
@Composable
private fun TopBarPrev() {
    TopBar(Modifier, rememberNavController())
}