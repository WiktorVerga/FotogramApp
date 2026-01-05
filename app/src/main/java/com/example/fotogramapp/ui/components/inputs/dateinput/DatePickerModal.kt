package com.example.fotogramapp.ui.components.inputs.dateinput

import android.widget.DatePicker
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import java.util.Calendar
import java.util.TimeZone

@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    // == Date Picker State ==
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcMillisecondsSinceEpoch: Long): Boolean {

                val maxCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
                    add(Calendar.YEAR, -13)
                }

                val maxDate = maxCalendar.timeInMillis

                return utcMillisecondsSinceEpoch <= maxDate
            }
        },
        yearRange = 1920..(Calendar.getInstance().get(Calendar.YEAR) - 13)
    )

    // == Dialog UI ==
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK", color = MaterialTheme.colorScheme.onSecondary, style = MaterialTheme.typography.labelLarge)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = MaterialTheme.colorScheme.onSecondary, style = MaterialTheme.typography.labelLarge)
            }
        },
    ) {
        DatePicker(
            state = datePickerState,
        )
    }
}

@Preview
@Composable
private fun DatePickerModalPrev() {
    DatePickerModal(onDateSelected = {}, onDismiss = {})

}
