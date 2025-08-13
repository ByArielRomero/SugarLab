package com.byariel.sugarlab.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.byariel.sugarlab.utils.ConverrtirStringToInt

@Composable
fun InputNumber(
    value: String,
    placeHolder:String,
    label:String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        label = { Text(label) },
        placeholder = { Text(placeHolder) },
        onValueChange = onValueChange,

        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun InputNumberPreview() {
    var previewValue by remember { mutableStateOf("") }


    InputNumber(
        value = previewValue,
        placeHolder = "ej:15",
        label = "Ingrese el año",
        onValueChange = { previewValue = it }
    )


}
