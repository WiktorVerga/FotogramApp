package com.example.fotogramapp.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.navigation.MapPage
import com.example.fotogramapp.navigation.Navigator
import com.example.fotogramapp.navigation.isRoute
import com.example.fotogramapp.ui.components.navbar.Navbar
import com.example.fotogramapp.ui.components.topbar.TopBar

// == Local Providers ==
val LocalNavController = staticCompositionLocalOf<NavController> {
    error("LocalNavController not provided")
}

val LocalSnackbar = staticCompositionLocalOf<SnackbarHostState> {
    error("LocalSnackbarHostState not provided")
}

@Composable
fun FotogramApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val snackbarHostState = remember { SnackbarHostState() }

    // == App Skeleton ==

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalSnackbar provides snackbarHostState
    ) {
        if (currentDestination.isRoute<MapPage>()) {
            //For Map Page
            Scaffold(
                modifier = modifier
                    .fillMaxSize(),
                topBar = {
                    TopBar()
                },
                bottomBar = {
                    Navbar()
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                },

            ) {innerPadding ->
                Box(modifier = Modifier.padding(innerPadding))
                Navigator(navController = navController)
            }

        } else {
            //For everything else
            Scaffold(
                modifier = modifier
                    .fillMaxSize(),
                topBar = {
                    TopBar()
                },
                bottomBar = {
                    Navbar()
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }

            ) { innerPadding ->
                Box(
                    Modifier
                        .padding(innerPadding)
                        .statusBarsPadding()
                        .padding(horizontal = 25.dp)
                ) {
                    Navigator(navController = navController)
                }
            }
        }

    }
}
