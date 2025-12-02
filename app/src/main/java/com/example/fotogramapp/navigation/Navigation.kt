package com.example.fotogramapp.navigation

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.HelloWorld

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SignUp,
    ) {
        composable<SignUp> {
            HelloWorld(navController = navController)
        }
        composable<Login> {
            val args = it.toRoute<Login>()

            Log.d("main", args.email)

            Text(
                text = args.email,
            )
            //TODO: Login Screen
        }
    }

}
