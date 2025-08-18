package com.byariel.sugarlab.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.logic.calcularPosicionFinal
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.draw.clipToBounds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobotScreen() {
    var pasosText by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf(Pair(0, 0)) }
    var mensajeError by remember { mutableStateOf("") }

    // Estado animado para las coordenadas del robot
    val robotOffset by animateOffsetAsState(
        targetValue = Offset(resultado.first.toFloat(), resultado.second.toFloat()),
        animationSpec = tween(durationMillis = 1000) // Animación de 1 segundo
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Alineado arriba para dejar espacio para la grilla
    ) {
        Text("¿Dónde está el Robot? 🤖", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        // Contenedor para la animación y la cuadrícula
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.LightGray)
                .clipToBounds() // No permite que el robot se salga de los límites
        ) {
            // Dibujamos el Canvas para la cuadrícula
            Canvas(modifier = Modifier.fillMaxSize()) {
                val gridSize = 20.dp.toPx()
                val totalWidth = size.width
                val totalHeight = size.height
                val centerX = totalWidth / 2
                val centerY = totalHeight / 2

                // Dibujar líneas de la cuadrícula
                for (x in 0..totalWidth.toInt() step gridSize.toInt()) {
                    drawLine(Color.Gray, Offset(x.toFloat(), 0f), Offset(x.toFloat(), totalHeight), strokeWidth = 1f)
                }
                for (y in 0..totalHeight.toInt() step gridSize.toInt()) {
                    drawLine(Color.Gray, Offset(0f, y.toFloat()), Offset(totalWidth, y.toFloat()), strokeWidth = 1f)
                }

                // Dibujar los ejes X e Y
                drawLine(Color.Red, Offset(0f, centerY), Offset(totalWidth, centerY), strokeWidth = 2f)
                drawLine(Color.Red, Offset(centerX, 0f), Offset(centerX, totalHeight), strokeWidth = 2f)
            }

            // El emoji del robot que se mueve
            Text(
                text = "🤖",
                fontSize = 30.sp,
                modifier = Modifier
                    .offset(
                        x = (150.dp + robotOffset.x.dp), // Sumamos 150 para centrarlo
                        y = (150.dp - robotOffset.y.dp) // Restamos para que Y positivo vaya hacia arriba
                    )
                    .align(Alignment.Center)
            )
        }

        Spacer(Modifier.height(16.dp))

        TextField(
            value = pasosText,
            onValueChange = { pasosText = it },
            label = { Text("Pasos (ej: 10, 5, -2)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                val pasos = pasosText.split(",").mapNotNull { it.trim().toIntOrNull() }
                if (pasos.isEmpty() && pasosText.isNotEmpty()) {
                    mensajeError = "Por favor, ingresa números válidos separados por comas."
                } else {
                    resultado = calcularPosicionFinal(pasos)
                    mensajeError = ""
                }
            }
        ) {
            Text("Calcular Posición")
        }

        Spacer(Modifier.height(16.dp))

        if (mensajeError.isNotEmpty()) {
            Text(mensajeError, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
        } else {
            Text("Coordenadas Finales:")
            Text(
                text = "(${resultado.first}, ${resultado.second})",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Preview
@Composable
fun RobotPreview(){
    RobotScreen()
}
