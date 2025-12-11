package com.example.fotogramapp.features.createpost

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavController
import com.example.fotogramapp.LocalAppDatabase
import com.example.fotogramapp.LocalDataStore
import com.example.fotogramapp.features.signup.SignupViewModel
import com.example.fotogramapp.navigation.LocalNavController
import com.example.fotogramapp.ui.components.buttons.PrimaryButton
import com.example.fotogramapp.ui.components.inputs.dateinput.DateInput
import com.example.fotogramapp.ui.components.inputs.imageinput.ImageInput
import com.example.fotogramapp.ui.components.inputs.textinput.TextInput
import com.example.fotogramapp.ui.components.title.LargeHeadline

@Composable
fun CreatePostPage(modifier: Modifier = Modifier) {
    val database = LocalAppDatabase.current
    val settingsRepository = LocalDataStore.current
    val navController = LocalNavController.current


    val viewModel: CreatePostViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                CreatePostViewModel(navController, database, settingsRepository)
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

            LargeHeadline("Create a New Post \uD83D\uDCDD")

            ImageInput(
                id = "image",
                title = "Pick an Image",
                getBitmapImage = viewModel.handleImage
            )

            TextInput(
                id = "message",
                title = "Write a Message",
                label = "Max size: 100 Characters",
                maxSize = 100,
                getSafeValue = viewModel.handleMessage
            )

            //TODO: Map Input

            PrimaryButton(
                modifier = modifier.padding(vertical = 50.dp),
                text = "Publish",
                onClick = viewModel.handlePublish
            )
        }
    }

}