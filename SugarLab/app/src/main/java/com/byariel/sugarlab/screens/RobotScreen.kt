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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobotScreen() {
    var pasosText by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf(Pair(0, 0)) }
    var mensajeError by remember { mutableStateOf("") }

    // Tamaño del canvas
    val canvasSizeDp = 300.dp
    val canvasSizePx = with(LocalDensity.current) { canvasSizeDp.toPx() }

    // Escalado para que los pasos se vean dentro del canvas
    val scaleFactor = 10f // 1 paso = 10px

    val robotOffset by animateOffsetAsState(
        targetValue = Offset(resultado.first * scaleFactor, resultado.second * scaleFactor),
        animationSpec = tween(durationMillis = 800)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("¿Dónde está el Robot? 🤖", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .size(canvasSizeDp)
                .background(Color.LightGray)
                .clipToBounds()
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val gridSize = 20.dp.toPx()
                val totalWidth = size.width
                val totalHeight = size.height
                val centerX = totalWidth / 2
                val centerY = totalHeight / 2

                // Dibujar líneas de cuadrícula
                for (x in 0..totalWidth.toInt() step gridSize.toInt()) {
                    drawLine(
                        Color.Gray,
                        Offset(x.toFloat(), 0f),
                        Offset(x.toFloat(), totalHeight),
                        1f
                    )
                }
                for (y in 0..totalHeight.toInt() step gridSize.toInt()) {
                    drawLine(
                        Color.Gray,
                        Offset(0f, y.toFloat()),
                        Offset(totalWidth, y.toFloat()),
                        1f
                    )
                }

                // Dibujar ejes X y Y
                drawLine(Color.Red, Offset(0f, centerY), Offset(totalWidth, centerY), 2f)
                drawLine(Color.Red, Offset(centerX, 0f), Offset(centerX, totalHeight), 2f)
            }

            // Tamaño del emoji en px (aprox)
            val robotSizePx =
                with(LocalDensity.current) { 30.sp.toPx() } // coincidir con fontSize del Text

// Emoji del robot centrado correctamente
            Text(
                text = "🤖",
                fontSize = 30.sp,
                modifier = Modifier.offset {
                    IntOffset(
                        x = (canvasSizePx / 2 + robotOffset.x - robotSizePx / 2).toInt(),
                        y = (canvasSizePx / 2 - robotOffset.y - robotSizePx / 2).toInt()
                    )
                }
            )

        }
        Spacer(Modifier.height(16.dp))

        TextField(
            value = pasosText,
            onValueChange = { pasosText = it },
            label = { Text("Pasos (ej: 10,5,-2)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
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
            Text(
                mensajeError,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        } else {
            Text("Coordenadas finales:")
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
fun RobotPreview() {
    RobotScreen()
}

