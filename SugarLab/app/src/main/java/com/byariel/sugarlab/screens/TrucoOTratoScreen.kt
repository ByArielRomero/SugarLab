package com.byariel.sugarlab.screens


import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.byariel.sugarlab.logic.GameViewModel

import com.byariel.sugarlab.logic.GamePhase


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val gameState by viewModel.gameState.collectAsState()
    val currentPlayer = (gameState.phase as? GamePhase.Playing)?.let {
        gameState.players.find { p -> p.id == it.currentPlayerId }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (val phase = gameState.phase) {
            is GamePhase.Start -> {
                Text(
                    text = "¡Truco o Trato!",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Button(onClick = { viewModel.startGame() }) {
                    Text("Empezar juego", fontSize = 24.sp)
                }
            }
            is GamePhase.Playing -> {
                // Pantalla de juego
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Jugador ${currentPlayer?.id}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tiempo: ${gameState.timer}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold

                    )
                    Text(
                        text = "Puntos: ${currentPlayer?.score}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                FlowRow(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.Center
                ) {
                    gameState.emojisOnScreen.forEach { emoji ->
                        Text(
                            text = emoji.emoji,
                            fontSize = 48.sp,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(64.dp)
                                .clickable {
                                    viewModel.onEmojiTapped(emoji.id)
                                }
                        )
                    }
                }
            }
            is GamePhase.Player1TurnEnded -> {
                Text(
                    text = "¡Turno de Jugador 1 terminado!",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Puntos de ${phase.player1.name}: ${phase.player1.score}",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Button(onClick = { viewModel.startNextTurn() }) {
                    Text("Empezar turno de Jugador 2", fontSize = 18.sp)
                }
            }
            is GamePhase.GameOver -> {
                val player1 = phase.players[0]
                val player2 = phase.players[1]
                Text(
                    text = "¡Juego Terminado!",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Puntos Jugador 1: ${player1.score}",
                    fontSize = 24.sp
                )
                Text(
                    text = "Puntos Jugador 2: ${player2.score}",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = when (val winner = phase.winner) {
                        null -> "¡Es un empate!"
                        else -> "¡El ganador es ${winner.name}!"
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                Button(onClick = { viewModel.startGame() }) {
                    Text("Volver a jugar", fontSize = 18.sp)
                }
            }
        }
    }
}
@Preview
@Composable
fun HalloweenScreenPreview(viewModel: GameViewModel = viewModel()){
    GameScreen(viewModel)
}