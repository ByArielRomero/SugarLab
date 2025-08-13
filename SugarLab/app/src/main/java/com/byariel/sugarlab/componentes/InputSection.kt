package com.byariel.sugarlab.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.byariel.sugarlab.ui.theme.EspaciadoMedio

@Composable
fun TituloSeccion(texto: String) {
    Text(
        texto,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = EspaciadoMedio)
    )
}