package com.example.fotogramapp.ui.components.inputs.dateinput

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateInputViewModel: ViewModel() {
    var showDatePicker by mutableStateOf(false)
        private set

    var selectedDate by mutableStateOf<Long?>(null)
        private set


    fun toggleDataPicker() {
        showDatePicker = !showDatePicker
    }

    val handleDateSelection: (Long?) -> Unit = { date ->
        selectedDate = date
    }

    fun setInitialDate(initialDate: String) {
        selectedDate = convertDateToMillis(initialDate)
    }

    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date(millis))
    }

    fun convertDateToMillis(dateString: String): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.parse(dateString)?.time ?: 0L
    }
}
