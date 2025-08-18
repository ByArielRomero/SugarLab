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
    // Texto ingresado por el usuario
    var coordenadasText by remember { mutableStateOf("") }

    // Posición actual del robot (X,Y)
    var resultado by remember { mutableStateOf(Pair(0, 0)) }

    // Historial de posiciones (rastro)
    var historial by remember { mutableStateOf(listOf<Pair<Int, Int>>()) }

    // Emoji del robot
    val robots = listOf("🤖","🐱","🐶","👾")
    var robotEmoji by remember { mutableStateOf(robots.random()) }

    // Mensaje de error si el input es incorrecto
    var mensajeError by remember { mutableStateOf("") }

    // Tamaño del canvas
    val canvasSizeDp = 300.dp
    val canvasSizePx = with(LocalDensity.current) { canvasSizeDp.toPx() }

    // Escalado: 1 unidad = 10px
    val scaleFactor = 10f

    // Animación para mover el robot suavemente
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
            // Dibujar cuadrícula y rastro
            Canvas(modifier = Modifier.fillMaxSize()) {
                val gridSize = 20.dp.toPx()
                val totalWidth = size.width
                val totalHeight = size.height
                val centerX = totalWidth / 2
                val centerY = totalHeight / 2

                // Líneas horizontales
                for (y in 0..totalHeight.toInt() step gridSize.toInt()) {
                    drawLine(Color.Gray, Offset(0f, y.toFloat()), Offset(totalWidth, y.toFloat()), 1f)
                }

                // Líneas verticales
                for (x in 0..totalWidth.toInt() step gridSize.toInt()) {
                    drawLine(Color.Gray, Offset(x.toFloat(), 0f), Offset(x.toFloat(), totalHeight), 1f)
                }

                // Ejes rojos
                drawLine(Color.Red, Offset(0f, centerY), Offset(totalWidth, centerY), 2f)
                drawLine(Color.Red, Offset(centerX, 0f), Offset(centerX, totalHeight), 2f)

                // Dibujar rastro del robot
                historial.forEach { pos ->
                    drawCircle(
                        color = Color.Blue,
                        radius = 5f,
                        center = Offset(
                            centerX + pos.first * scaleFactor,
                            centerY - pos.second * scaleFactor
                        )
                    )
                }
            }

            // Tamaño del emoji robot
            val robotSizePx = with(LocalDensity.current) { 30.sp.toPx() }

            // Dibujar robot en la posición actual con límites
            Text(
                text = robotEmoji,
                fontSize = 30.sp,
                modifier = Modifier.offset {
                    val maxOffset = canvasSizePx / 2 - robotSizePx / 2
                    val offsetX = robotOffset.x.coerceIn(-maxOffset, maxOffset)
                    val offsetY = robotOffset.y.coerceIn(-maxOffset, maxOffset)

                    IntOffset(
                        x = (canvasSizePx / 2 + offsetX - robotSizePx / 2).toInt(),
                        y = (canvasSizePx / 2 - offsetY - robotSizePx / 2).toInt()
                    )
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        // Input de coordenadas
        TextField(
            value = coordenadasText,
            onValueChange = { coordenadasText = it },
            label = { Text("Coordenadas X,Y (ej: 5,4)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )

        Spacer(Modifier.height(16.dp))

        // Botón para mover robot
        Button(
            onClick = {
                val coords = coordenadasText.split(",").mapNotNull { it.trim().toIntOrNull() }
                if (coords.size != 2) {
                    mensajeError = "Por favor ingresa dos números separados por coma: X,Y"
                } else {
                    // Limitar coordenadas
                    val maxCoord = ((canvasSizePx / 2) / scaleFactor).toInt()
                    val x = coords[0].coerceIn(-maxCoord, maxCoord)
                    val y = coords[1].coerceIn(-maxCoord, maxCoord)

                    // Actualizar posición y rastro
                    resultado = Pair(x, y)
                    historial = historial + resultado

                    // Cambiar emoji
                    robotEmoji = robots.random()

                    mensajeError = ""
                }
            }
        ) {
            Text("Mover Robot")
        }

        Spacer(Modifier.height(16.dp))

        // Mostrar mensaje de error o coordenadas
        if (mensajeError.isNotEmpty()) {
            Text(mensajeError, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
        } else {
            Text("Coordenadas actuales:")
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
