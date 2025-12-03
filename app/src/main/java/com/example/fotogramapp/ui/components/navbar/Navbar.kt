package com.example.fotogramapp.ui.components.navbar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fotogramapp.navigation.*
import com.example.fotogramapp.ui.theme.Icons

@Composable
fun Navbar(modifier: Modifier = Modifier, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destination = navBackStackEntry?.destination
    val currentRoute = destination?.route


    NavigationBar(
        modifier =
            modifier
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
        windowInsets = NavigationBarDefaults.windowInsets
    ) {
        NavigationBarItem(
            selected = destination?.equals(Discover) == true,
            onClick = {
                navController.navigate(Discover)
            },
            icon = {
                Icon(
                    painter = painterResource(Icons.Discover),
                    contentDescription = "Discover"
                )
            },
        )

        NavigationBarItem(
            selected = destination?.equals(Map) == true,
            onClick = {
                navController.navigate(Map(
                    startingLongitude = 49.0,
                    startingLatitude = 10.0,
                    zoom = 10.0
                ))
            },
            icon = {
                Icon(
                    painter = painterResource(Icons.MapPin),
                    contentDescription = "Map"
                )
            },
        )

        NavigationBarItem(
            selected = destination?.equals(Profile) == true,
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
