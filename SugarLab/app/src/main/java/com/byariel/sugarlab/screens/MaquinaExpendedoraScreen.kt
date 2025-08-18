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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.byariel.sugarlab.logic.Producto
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun MaquinaExpendedoraScreen() {
    val vm = remember { MaquinaExpendedora() }
    var resultado by remember { mutableStateOf("") }
    val productos = vm.getProductos()
    var saldo by remember { mutableStateOf(vm.getSaldo()) }
    var dineroIngresado by remember { mutableStateOf("") }
    var historial by remember { mutableStateOf(listOf<String>()) } // 📜 historial

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🥤 Máquina Expendedora", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(16.dp))

        Text("💰 Saldo: $${saldo}", style = MaterialTheme.typography.titleMedium)

        Spacer(Modifier.height(16.dp))

        // Campo para ingresar dinero
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = dineroIngresado,
                onValueChange = { dineroIngresado = it },
                label = { Text("Ingresar dinero ($)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    val monto = dineroIngresado.toIntOrNull()
                    if (monto == null || monto <= 0) {
                        resultado = "⚠️ Ingresa un monto válido."
                    } else {
                        vm.ingresarDinero(monto)
                        saldo = vm.getSaldo()
                        dineroIngresado = ""
                        resultado = "💵 Se agregaron $$monto"
                        historial = historial + "💵 Ingresaste $$monto (Saldo: $$saldo)"
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Añadir")
            }
        }

        Spacer(Modifier.height(16.dp))

        productos.forEach { producto ->
            ProductoCard(
                producto = producto,
                onClick = {
                    val compra = vm.comprar(producto.id) // 👈 esto ya devuelve el mensaje correcto
                    saldo = vm.getSaldo()
                    resultado = compra

                    // Guardamos en historial sin recalcular "faltó"
                    historial = historial + when {
                        compra.startsWith("¡Compraste") ->
                            "✅ ${producto.nombre} - $${producto.precio} | Saldo: $$saldo"
                        compra.startsWith("Saldo insuficiente") ->
                            "❌ ${producto.nombre} | $compra"
                        else -> compra
                    }
                }
            )
        }

        Spacer(Modifier.height(24.dp))

        // Botón para devolver el cambio
        Button(
            onClick = {
                val cambio = vm.devolverCambio()
                saldo = vm.getSaldo()
                resultado = if (cambio > 0) {
                    "💸 Te devolvimos $$cambio de cambio."
                } else {
                    "😅 No hay saldo para devolver."
                }
                historial = historial + "🔄 Cambio devuelto: $$cambio"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Devolver cambio")
        }

        Spacer(Modifier.height(16.dp))

        if (resultado.isNotBlank()) {
            Text(resultado, style = MaterialTheme.typography.titleMedium)
        }

        Spacer(Modifier.height(24.dp))

        // Historial de compras 📜
        Text("📜 Historial:", style = MaterialTheme.typography.titleMedium)
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            items(historial) { item ->
                Text("• $item")
            }
        }
    }
}

@Composable
fun ProductoCard(producto: Producto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("${producto.id}. ${producto.nombre}")
            Text("$${producto.precio}")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MaquinaExpendedoraPreview() {
    MaterialTheme {
        MaquinaExpendedoraScreen()
    }
}

