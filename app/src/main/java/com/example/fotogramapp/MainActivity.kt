package com.example.fotogramapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.fotogramapp.app.FotogramApp
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.repository.PostRepository
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.data.repository.UserRepository
import com.example.fotogramapp.ui.theme.FotogramTheme
import com.example.testing_apis.model.RemoteDataSource

//DataStore
private val Context.dataStore by preferencesDataStore(name = "settings")

// == Local Providers ==
val LocalAppDatabase = staticCompositionLocalOf<AppDatabase> {
    error("LocalAppDatabase not provided")
}

val LocalDataStore = staticCompositionLocalOf<SettingsRepository> {
    error("LocalAppDatabase not provided")
}

val LocalUserRepository = staticCompositionLocalOf<UserRepository> {
    error("LocalUserRepository not provided")
}

val LocalPostRepository = staticCompositionLocalOf<PostRepository> {
    error("LocalUserRepository not provided")
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Creating SettingsRepository
        val settingsDataStore = applicationContext.dataStore
        val settingsRepository = SettingsRepository(settingsDataStore)

        enableEdgeToEdge()
        setContent {
            //Instance of db
            val db = remember {
                Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    "fotogram-database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }

            //Instances of Repositories
            val remoteDataSource = RemoteDataSource()
            val userRepo = UserRepository(settingsRepository, remoteDataSource)
            val postRepo = PostRepository(db, settingsRepository, remoteDataSource)

            //Local Providers for Dependency Injection
            CompositionLocalProvider(
                LocalAppDatabase provides db,
                LocalDataStore provides settingsRepository,
                LocalUserRepository provides userRepo,
                LocalPostRepository provides postRepo
            ) {
                FotogramTheme() {
                    FotogramApp()
                }
            }
        }
    }
}