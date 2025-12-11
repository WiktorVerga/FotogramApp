package com.example.fotogramapp.ui.components.inputs.textinput

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fotogramapp.ui.theme.FotogramTheme
import com.mapbox.maps.extension.style.expressions.dsl.generated.color
import org.w3c.dom.Text

@Composable
fun TextInput(modifier: Modifier = Modifier,id: String, title: String = "Title", label: String = "Label", minSize: Int = 0, maxSize: Int = 100, getSafeValue: (String) -> Unit) {

    //Quando uso viewmodel mi si collegano le singole istanze?
    val textInputVM: TextInputViewModel = viewModel(key = id)


    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (textInputVM.hasError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceContainer,
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

            Text(label, style = MaterialTheme.typography.labelSmall)
            if (textInputVM.hasError)
                Text(textInputVM.errorText, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)

            BasicTextField(
                value = textInputVM.text,
                onValueChange = {
                    textInputVM.onTextChange(it)
                    textInputVM.handleValue(minSize, maxSize, getSafeValue)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Light,
                ),
                decorationBox = { innerTextField ->

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)

                    ) {
                        if (textInputVM.text.isEmpty()) {
                            Text(
                                "Type something here...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }

}

@Preview(showBackground = false)
@Composable
private fun TextInputPrev() {
    FotogramTheme {
        TextInput(Modifier, "preview", "Title", "Label") {}
    }

}