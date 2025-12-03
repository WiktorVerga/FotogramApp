package com.example.fotogramapp.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.navigation.Navigator
import com.example.fotogramapp.ui.components.navbar.Navbar

@Composable
fun FotogramApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier
        .fillMaxSize(),
        bottomBar = {
            Navbar(navController = navController)
        },
        topBar = {}
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