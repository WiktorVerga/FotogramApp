package com.example.fotogramapp.ui.components.navbar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.navigation.*
import com.example.fotogramapp.ui.theme.Icons

@Composable
fun Navbar(modifier: Modifier = Modifier, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination


    NavigationBar(
        modifier =
            modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = MaterialTheme.colorScheme.secondary,
    ) {
        NavigationBarItem(
            selected = currentDestination.isRoute<Discover>(),
            onClick = {
                navController.navigate(Discover)
            },
            icon = {
                Icon(
                    painter = painterResource(Icons.Discover),
                    contentDescription = "Discover"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )

        NavigationBarItem(
            selected = currentDestination.isRoute<MapPage>(),
            onClick = {
                navController.navigate(MapPage(
                    startingLongitude = 49.0,
                    startingLatitude = 10.0,
                    zoom = 10.0
                ))
            },
            icon = {
                Icon(
                    painter = painterResource(Icons.MapPin),
                    contentDescription = "MapPage"
                )
            },
        )

        NavigationBarItem(
            selected = currentDestination.isRoute<Profile>(),
            onClick = {
                navController.navigate(Profile(
                    id = "1",
                    isUsers = true
                ))
            },
            icon = {
                Icon(
                    painter = painterResource(Icons.Profile),
                    contentDescription = "Discover"
                )
            },
        )
    }
}

@Preview
@Composable
private fun NavbarPrev() {
    Navbar(Modifier, rememberNavController())
    
}