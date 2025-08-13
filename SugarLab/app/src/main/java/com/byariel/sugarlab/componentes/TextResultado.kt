package com.byariel.sugarlab.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.byariel.sugarlab.ui.theme.EspaciadoMedio
import com.byariel.sugarlab.ui.theme.SugarLabTheme

@Composable
fun TextResultado (texto: String , modifier: Modifier){

    Text(
        text = texto,
        modifier = modifier .padding(EspaciadoMedio),

    )

}

@Preview(showBackground = true)
@Composable
fun txtPreview(){
    SugarLabTheme {
        TextResultado("Hola Mundo", Modifier)
    }
}