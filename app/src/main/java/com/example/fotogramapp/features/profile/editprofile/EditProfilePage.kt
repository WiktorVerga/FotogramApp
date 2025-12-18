package com.example.fotogramapp.features.profile.editprofile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fotogramapp.LocalDataStore
import com.example.fotogramapp.LocalUserRepository
import com.example.fotogramapp.app.LocalNavController
import com.example.fotogramapp.app.LocalSnackbar
import com.example.fotogramapp.domain.model.User
import com.example.fotogramapp.ui.components.buttons.PrimaryButton
import com.example.fotogramapp.ui.components.inputs.dateinput.DateInput
import com.example.fotogramapp.ui.components.inputs.imageinput.ImageInput
import com.example.fotogramapp.ui.components.inputs.textinput.TextInput
import com.example.fotogramapp.ui.components.title.LargeHeadline

@Composable
fun EditProfilePage(modifier: Modifier = Modifier, username: String, biography: String, dob: String, image: String) {
    val snackBarHostState = LocalSnackbar.current
    val userRepo = LocalUserRepository.current
    val navController = LocalNavController.current

    val viewModel: EditProfileViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                EditProfileViewModel(
                    snackBarHostState = snackBarHostState,
                    userRepo = userRepo,
                    navController = navController
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
            LargeHeadline("Edit your Profile")

            TextInput(
                id = "edit-username",
                title = "Username",
                label = "Max size: 15 Characters",
                maxSize = 15,
                getSafeValue = viewModel.handleUsername,
                initialText = username
            )

            TextInput(
                id = "edit-Biography",
                title = "Biography",
                label = "Max size: 100 Characters",
                maxSize = 100,
                getSafeValue = viewModel.handleBiography,
                initialText = biography
            )

            DateInput(
                id = "edit-birth_date",
                title = "Date of Birth",
                getStringeDate = viewModel.handleDob,
                initialDate = dob
            )

            ImageInput(
                id = "edit-pfp",
                title = "Profile Picture",
                getBase64Image = viewModel.handleImage,
                isPfp = true,
                initialImage = image
            )

            PrimaryButton(
                modifier = modifier.padding(vertical = 50.dp),
                text = "Save Changes",
                onClick = viewModel.handleSave
            )
        }
    }
}