package com.example.fotogramapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.navigation.Login
import com.example.fotogramapp.navigation.Navigation
import com.example.fotogramapp.ui.theme.FotogramTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FotogramTheme() {
                DefaultScaffold {
                    Navigation()
                }
            }
        }
    }
}

@Composable
fun DefaultScaffold(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Scaffold(modifier = modifier
        .fillMaxSize()
    ) { innerPadding ->
        Box(
            Modifier
                .statusBarsPadding()
                .padding(horizontal = 25.dp)
        ) {
            content()
        }
    }
}

@Composable
fun HelloWorld(modifier: Modifier = Modifier, navController: NavHostController = rememberNavController()) {
    Column(
        modifier
    ) {
        Text("Scopri nuovi luoghi", style = MaterialTheme.typography.headlineLarge)
        Text("Connettiti attraverso le immagini", style = MaterialTheme.typography.bodyLarge)
        Button(
            onClick = {
                navController.navigate(Login("test", "test"))
            },

            ) {
            Text("Inizia", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FotogramTheme(true) {
        DefaultScaffold {
            HelloWorld()
        }
    }
}