package com.byariel.sugarlab.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.byariel.sugarlab.logic.MaquinaExpendedora

@Composable
fun MaquinaExpendedoraScreen(vm: MaquinaExpendedora = MaquinaExpendedora()) {
    var resultado by remember { mutableStateOf("") }
    val productos = vm.getProductos()
    var saldo by remember { mutableStateOf(vm.getSaldo()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🥤 Máquina Expendedora", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Text("💰 Saldo: $saldo¢", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(16.dp))

        // Lista de productos
        productos.forEach { producto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable {
                        resultado = vm.comprar(producto.id)
                        saldo = vm.getSaldo()
                    },
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${producto.id}. ${producto.nombre}")
                    Text("${producto.precio}¢")
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        if (resultado.isNotBlank()) {
            Text(resultado, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MaquinaExpendedoraPreview() {
    MaterialTheme {
        MaquinaExpendedoraScreen(vm = MaquinaExpendedora())
    }
}

