package com.example.fotogramapp.features.signup

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fotogramapp.LocalDataStore
import com.example.fotogramapp.app.LocalSnackbar
import com.example.fotogramapp.data.database.AppDatabase
import com.example.fotogramapp.data.repository.SettingsRepository
import com.example.fotogramapp.navigation.LocalNavController
import com.example.fotogramapp.ui.components.buttons.PrimaryButton
import com.example.fotogramapp.ui.components.inputs.dateinput.DateInput
import com.example.fotogramapp.ui.components.inputs.imageinput.ImageInput
import com.example.fotogramapp.ui.components.inputs.textinput.TextInput
import com.example.fotogramapp.ui.components.post.PostCardViewModel
import com.example.fotogramapp.ui.theme.FotogramTheme

@Composable
fun SignupPage(modifier: Modifier = Modifier) {

    val navController = LocalNavController.current
    val settingsRepository = LocalDataStore.current
    val snackbarHostState = LocalSnackbar.current

    val viewModel: SignupViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                SignupViewModel(
                    navController = navController,
                    settingsRepository = settingsRepository,
                    snackBarHostState = snackbarHostState
                )
            }
        }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                text = "Sign Up",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp, bottom = 40.dp),
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
            )

            TextInput(
                id = "username",
                title = "Username",
                label = "Max size: 15 Characters",
                maxSize = 15,
                getSafeValue = viewModel.handleUsername
            )

            TextInput(
                id = "Biography",
                title = "Biography",
                label = "Max size: 100 Characters",
                maxSize = 100,
                getSafeValue = viewModel.handleBiography
            )

            DateInput(
                id = "birth_date",
                title = "Date of Birth",
                getStringeDate = viewModel.handleDob
            )

            ImageInput(
                id = "pfp",
                title = "Profile Picture",
                getBase64Image = viewModel.handleImage,
                isPfp = true
            )

            PrimaryButton(
                modifier = modifier.padding(vertical = 50.dp),
                text = "Sign Up",
                onClick = viewModel.handleSignup
            )
        }
    }
}