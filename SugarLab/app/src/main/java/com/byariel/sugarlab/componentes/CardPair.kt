package com.byariel.sugarlab.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byariel.sugarlab.ui.theme.EspaciadoMedio


@Composable
fun CardPair(
    texto: String,
    texto2: String,
    colorTexto: Color = MaterialTheme.colorScheme.onSurface,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.padding(EspaciadoMedio),
        shape = RoundedCornerShape(EspaciadoMedio),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(EspaciadoMedio),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.titleLarge,
                color = colorTexto
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = texto2,
                style = MaterialTheme.typography.titleLarge,
                color = colorTexto
            )
        }
    }
}


@Preview
@Composable
fun CardPairPreview(){
    CardPair("Resultado","Resultado" )
}