package com.byariel.sugarlab.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.logic.calcularArea
import java.nio.file.WatchEvent


@Preview
@Composable
fun PoligonoScreen() {
    var tipoSeleccionado by remember { mutableStateOf("cuadrado") }
    var lado1 by remember { mutableStateOf("") }
    var lado2 by remember { mutableStateOf("") }
    var area by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "📐 Área Polígono",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Elige el tipo de polígono:",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        // Mantengo dos botones en fila con peso igual
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {
                    tipoSeleccionado = "cuadrado"  // minúsculas para consistencia
                    lado1 = ""
                    lado2 = ""
                    area = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("CUADRADO")
            }

            Button(
                onClick = {
                    tipoSeleccionado = "triangulo"
                    lado1 = ""
                    lado2 = ""
                    area = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("TRIÁNGULO")
            }
        }

        // Botón de rectángulo más pequeño abajo centrado
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    tipoSeleccionado = "rectangulo"
                    lado1 = ""
                    lado2 = ""
                    area = ""
                },
                modifier = Modifier.width(150.dp)
            ) {
                Text("RECTÁNGULO")
            }
        }

        // Campos de entrada
        OutlinedTextField(
            value = lado1,
            onValueChange = { lado1 = it },
            label = {
                if (tipoSeleccionado == "cuadrado") Text("Lado")
                else Text("Base")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (tipoSeleccionado != "cuadrado") {
            OutlinedTextField(
                value = lado2,
                onValueChange = { lado2 = it },
                label = { Text("Altura") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                // 1. Intentamos convertir los textos de los campos a números (Double)
                val valor1 = lado1.toDoubleOrNull() ?: 0.0
                val valor2 = lado2.toDoubleOrNull() ?: 0.0
                val resultado = calcularArea(tipoSeleccionado, valor1, valor2)
                area = "Área: $resultado"
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("CALCULAR")
        }

        Text(text = area)
    }

    PoligonoAyuda()
}

@Composable
fun PoligonoAyuda() {
    var mostrarDialogo by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Button(colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF1976D2),  // color de fondo
            contentColor = Color.White            // color del texto
        ),onClick = { mostrarDialogo = true }) {
            Text("¿Cómo funciona?")
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                confirmButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("Entendido")
                    }
                },
                title = { Text("Reglas del reto") },
                text = {
                    Text(
                        """
                        📐 Calculadora de áreas

                        Podés calcular el área de un triángulo, un rectángulo o un cuadrado.

                        Solo seleccioná el tipo de figura y completá sus lados.

                        El juego hace el cálculo automáticamente.


                        """.trimIndent()
                    )
                }
            )
        }
    }
}