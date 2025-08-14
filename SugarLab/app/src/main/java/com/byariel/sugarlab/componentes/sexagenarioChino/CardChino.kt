package com.byariel.sugarlab.componentes.sexagenarioChino

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.byariel.sugarlab.logic.SignoChino

@Composable
fun SignoChinoCard(signo: SignoChino) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = signo.imagenRes),
            contentDescription = signo.animal,
            modifier = Modifier.height(150.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${signo.elemento} - ${signo.animal}",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = signo.caracteristicas,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
