package com.example.fotogramapp.ui.components.inputs.textinput

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TextInputViewModel : ViewModel() {
    var text by mutableStateOf("")
        private set

    var errorText by mutableStateOf("")
        private set
    var hasError by mutableStateOf(false)
        private set


    // Funzione per aggiornare il testo
    fun onTextChange(newText: String) {
        text = newText
    }

    //Funzione per Controllo Errori
    fun checkForErrors(minSize: Int, maxSize: Int) =  !(text.length in minSize..maxSize)

    //Funzione per gestione Errori
    fun handleValue(minSize: Int, maxSize: Int, getValidText: (String) -> Unit) {
        hasError = checkForErrors(minSize, maxSize)

        if (hasError) {
            errorText = "Text lenght is not valid"
        }
        else {
            errorText = ""
            hasError = false
            getValidText(text)
        }
    }

    fun setInitialText(initialText: String) {
        this.text = initialText
    }
}