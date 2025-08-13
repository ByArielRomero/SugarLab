package com.byariel.sugarlab.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.byariel.sugarlab.ui.theme.EspaciadoMedio
import com.byariel.sugarlab.ui.theme.RadioCard

@Composable
fun CardResultado(
    texto: String,
    colorTexto: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(EspaciadoMedio),
        shape = RoundedCornerShape(RadioCard),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Text(
            texto,
            style = MaterialTheme.typography.bodyMedium,
            color = colorTexto,
            modifier = Modifier.padding(EspaciadoMedio)
        )
    }
}

@Preview
@Composable
fun CardResultado(){
    CardResultado("Resultado")
}