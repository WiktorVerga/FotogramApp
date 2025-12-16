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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.navigation.LocalNavController
import com.example.fotogramapp.navigation.Navigator
import com.example.fotogramapp.ui.components.navbar.Navbar
import com.example.fotogramapp.ui.components.topbar.TopBar

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("LocalNavController not provided")
}

val LocalSnackbar = staticCompositionLocalOf<SnackbarHostState> {
    error("LocalSnackbarHostState not provided")
}

@Composable
fun FotogramApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    val snackbarHostState = remember { SnackbarHostState() }

    CompositionLocalProvider(
        LocalNavController provides navController,
        LocalSnackbar provides snackbarHostState
    ) {
        Scaffold(
            modifier = modifier
                .fillMaxSize(),
            topBar = {
                TopBar(navController = navController)
            },
            bottomBar = {
                Navbar(navController = navController)
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
