package com.byariel.sugarlab.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.R
import com.byariel.sugarlab.logic.piedraPapelTijeraLogic
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun PiedraPapelTijeraScreen() {
    // Estado selección Player 1 (manual)
    var seleccionPlayer1 by remember { mutableStateOf("") }
    // Estado selección Player 2 (automática)
    var seleccionPlayer2 by remember { mutableStateOf("") }
    // Lista jugadas realizadas
    val jugadas = remember { mutableStateListOf<Pair<String, String>>() }
    // Estado resultado (rondas o juego)
    var resultado by remember { mutableStateOf("Partida en curso") }

    // Verifica si el juego terminó (alguien ganó el juego o empate)
    val juegoTerminado = resultado.contains("gana el juego") || resultado == "Empate"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "\uD83E\uDEA8Piedra, \uD83D\uDCDDpapel o ✂\uFE0Ftijera",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(50.dp))

        // Selección Player 1
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            SelectionCard(
                imageRes = R.drawable.piedra,
                isSelected = seleccionPlayer1 == "R",
                enabled = !juegoTerminado,
                onClick = { seleccionPlayer1 = "R" }
            )
            Spacer(modifier = Modifier.width(10.dp))
            SelectionCard(
                imageRes = R.drawable.papel,
                isSelected = seleccionPlayer1 == "P",
                enabled = !juegoTerminado,
                onClick = { seleccionPlayer1 = "P" }
            )
            Spacer(modifier = Modifier.width(10.dp))
            SelectionCard(
                imageRes = R.drawable.tijera,
                isSelected = seleccionPlayer1 == "S",
                enabled = !juegoTerminado,
                onClick = { seleccionPlayer1 = "S" }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Imagen VS
        Image(
            painter = painterResource(id = R.drawable.vs),
            contentDescription = "Versus",
            modifier = Modifier
                .size(180.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Selección Player 2 (solo para mostrar, no clickeable)
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            SelectionCard(
                imageRes = R.drawable.piedra,
                isSelected = seleccionPlayer2 == "R",
                enabled = false,
                onClick = {}
            )
            Spacer(modifier = Modifier.width(10.dp))
            SelectionCard(
                imageRes = R.drawable.papel,
                isSelected = seleccionPlayer2 == "P",
                enabled = false,
                onClick = {}
            )
            Spacer(modifier = Modifier.width(10.dp))
            SelectionCard(
                imageRes = R.drawable.tijera,
                isSelected = seleccionPlayer2 == "S",
                enabled = false,
                onClick = {}
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Resultado actual
        Text(
            text = resultado,
            fontSize = when {
                resultado.contains("gana") -> 32.sp
                resultado == "Empate" -> 28.sp
                else -> 24.sp
            },
            fontWeight = FontWeight.Bold,
            color = when {
                resultado.contains("gana") -> Color(0xFF388E3C)
                resultado == "Empate" -> Color(0xFFD32F2F)
                else -> Color.Unspecified
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botones Jugar y Reiniciar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (seleccionPlayer1.isNotEmpty() && !juegoTerminado) {
                        // Selección aleatoria Player 2
                        val opciones = listOf("R", "P", "S")
                        seleccionPlayer2 = opciones.random()

                        // Agregar jugada
                        jugadas.add(seleccionPlayer1 to seleccionPlayer2)

                        // Actualizar resultado con la lógica
                        resultado = piedraPapelTijeraLogic(jugadas)

                        // Limpiar selección Player 1 para siguiente ronda
                        seleccionPlayer1 = ""
                        // Player 2 se mantiene visible para mostrar selección
                    } else if (!juegoTerminado) {
                        resultado = "Jugador 1 debe elegir"
                    }
                },
                enabled = !juegoTerminado
            ) {
                Text("Jugar")
            }

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedButton(
                onClick = {
                    // Reiniciar todo
                    seleccionPlayer1 = ""
                    seleccionPlayer2 = ""
                    jugadas.clear()
                    resultado = "Partida en curso"
                }
            ) {
                Text("Reiniciar")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Historial de jugadas horizontal
        if (jugadas.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                jugadas.forEachIndexed { index, (j1, j2) ->
                    Card(
                        modifier = Modifier.size(width = 140.dp, height = 60.dp),
                        border = BorderStroke(
                            width = 3.dp,
                            color = Color(0xFF388E3C)
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            Text(
                                "Ronda ${index + 1}: $j1 vs $j2",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Carta de selección con imagen y borde destacado si está seleccionada.
 * @param imageRes recurso de imagen
 * @param isSelected si está seleccionada para mostrar borde
 * @param enabled si está habilitada para clic
 * @param onClick lambda al hacer clic
 */
@Composable
fun SelectionCard(
    imageRes: Int,
    isSelected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .border(
                width = if (isSelected) 4.dp else 1.dp,
                color = if (isSelected) Color(0xFF388E3C) else Color.Gray,
                shape = MaterialTheme.shapes.medium
            ),
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = enabled) {
                    onClick()
                }
        )
    }
}
