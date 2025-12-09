package com.example.fotogramapp.ui.components.inputs.dateinput

import androidx.compose.material3.TextButton
import com.example.fotogramapp.ui.components.inputs.textinput.TextInputViewModel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fotogramapp.ui.theme.FotogramTheme
import com.mapbox.maps.extension.style.expressions.dsl.generated.color

@Composable
fun DateInput(modifier: Modifier = Modifier, id: String, title: String = "Title", getStringeDate: (String) -> Unit) {

    val dateInputVM: DateInputViewModel = viewModel(key = id)

    LaunchedEffect(dateInputVM.selectedDate) {
        if (dateInputVM.selectedDate != null) {
            getStringeDate(dateInputVM.convertMillisToDate(dateInputVM.selectedDate ?: 0))
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = RoundedCornerShape(30.dp)
            ),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)

        ) {
            Text(title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.ExtraBold
            )


            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable {
                        dateInputVM.toggleDataPicker()
                    },
            ) {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    text = "Select from the Calendar",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                dateInputVM.selectedDate?.let {
                    Text(
                        text = dateInputVM.convertMillisToDate(it),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }


        }

        if (dateInputVM.showDatePicker)
            DatePickerModal(onDateSelected = dateInputVM.handleDateSelection, onDismiss = { dateInputVM.toggleDataPicker() })
    }

}


@Preview
@Composable
private fun DateInputPrev() {
    FotogramTheme() {
        DateInput(
            id = "datePreview",
            title = "",
            getStringeDate = { },
        )
    }

}