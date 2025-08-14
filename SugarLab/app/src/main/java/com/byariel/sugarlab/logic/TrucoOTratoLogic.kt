package com.byariel.sugarlab.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

// --- Clases para representar los jugadores y el estado del juego ---
data class Player(val id: Int, val name: String, val score: Int)
data class InteractiveEmoji(val id: Int, val emoji: String, val type: EmojiType)
enum class EmojiType { TREAT, TRICK }

// Fases del juego
sealed class GamePhase {
    data object Start : GamePhase()
    data class Playing(val currentPlayerId: Int) : GamePhase()
    data class Player1TurnEnded(val player1: Player) : GamePhase()
    data class GameOver(val winner: Player?, val players: List<Player>) : GamePhase()
}

// El estado del juego
data class GameState(
    val phase: GamePhase = GamePhase.Start,
    val players: List<Player> = listOf(Player(1, "Jugador 1", 0), Player(2, "Jugador 2", 0)),
    val emojisOnScreen: List<InteractiveEmoji> = emptyList(),
    val timer: Int = 5 // Temporizador de 15 segundos
)

class GameViewModel : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> = _gameState.asStateFlow()

    private val allTreats = listOf("🍭", "🍬", "🍫", "🍩", "🧁", "🍪")
    private val allTricks = listOf("👻", "💀", "🕷", "🕸", "🦇")
    private val totalEmojis = 45 // Aumentamos la cantidad de emojis

    fun startGame() {
        val initialEmojis = (1..totalEmojis).map {
            val isTrick = Random.nextBoolean()
            val type = if (isTrick) EmojiType.TRICK else EmojiType.TREAT
            val emoji = if (isTrick) allTricks.random() else allTreats.random()
            InteractiveEmoji(it, emoji, type)
        }
        _gameState.value = GameState(
            phase = GamePhase.Playing(1), // Empieza el turno del Jugador 1
            emojisOnScreen = initialEmojis
        )
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            var time = 5
            while (time > 0 && _gameState.value.phase is GamePhase.Playing) {
                _gameState.value = _gameState.value.copy(timer = time)
                delay(1000)
                time--
            }
            // Cuando el tiempo se agota, se pasa a la siguiente fase
            if (time == 0) {
                handleEndOfTurn()
            }
        }
    }

    private fun handleEndOfTurn() {
        val currentPhase = _gameState.value.phase
        if (currentPhase is GamePhase.Playing) {
            val currentPlayerId = currentPhase.currentPlayerId
            val players = _gameState.value.players
            if (currentPlayerId == 1) {
                _gameState.value = _gameState.value.copy(phase = GamePhase.Player1TurnEnded(players[0]))
            } else {
                _gameState.value = _gameState.value.copy(phase = GamePhase.GameOver(determineWinner(), players))
            }
        }
    }

    fun startNextTurn() {
        _gameState.value = _gameState.value.copy(
            phase = GamePhase.Playing(2), // Empieza el turno del Jugador 2
            emojisOnScreen = (1..totalEmojis).map { // Reinicia los emojis
                val isTrick = Random.nextBoolean()
                val type = if (isTrick) EmojiType.TRICK else EmojiType.TREAT
                val emoji = if (isTrick) allTricks.random() else allTreats.random()
                InteractiveEmoji(it, emoji, type)
            },
            timer = 15 // Reinicia el temporizador
        )
        startTimer()
    }

    fun onEmojiTapped(emojiId: Int) {
        val currentPhase = _gameState.value.phase
        if (currentPhase !is GamePhase.Playing) return

        val tappedEmoji = _gameState.value.emojisOnScreen.find { it.id == emojiId }
        tappedEmoji?.let { emoji ->
            val currentPlayerId = currentPhase.currentPlayerId
            val players = _gameState.value.players.toMutableList()
            val currentPlayerIndex = players.indexOfFirst { it.id == currentPlayerId }

            val newScore = if (emoji.type == EmojiType.TREAT) {
                players[currentPlayerIndex].score + 10
            } else {
                players[currentPlayerIndex].score - 5
            }
            players[currentPlayerIndex] = players[currentPlayerIndex].copy(score = newScore)

            val updatedEmojis = _gameState.value.emojisOnScreen.filter { it.id != emojiId }

            _gameState.value = _gameState.value.copy(
                players = players,
                emojisOnScreen = updatedEmojis
            )
        }
    }

    private fun determineWinner(): Player? {
        val players = _gameState.value.players
        val player1 = players[0]
        val player2 = players[1]
        return when {
            player1.score > player2.score -> player1
            player2.score > player1.score -> player2
            else -> null // Empate
        }
    }
}