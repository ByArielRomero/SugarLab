package com.byariel.sugarlab.logic

/**
 * Función que recibe la lista de jugadas y devuelve el estado actual del juego.
 * Cada jugada es un par (jugador1, jugador2) con valores "R", "P" o "S".
 *
 * Retorna:
 * - "Player X gana la ronda N" si alguien ganó esa ronda.
 * - "Player X gana el juego" si alguien llegó a 2 puntos (mejor de 3).
 * - "Empate" si se jugaron 3 rondas y nadie ganó.
 * - "Partida en curso" mientras no haya ganador final.
 */
fun piedraPapelTijeraLogic(jugadas: List<Pair<String, String>>): String {
    var puntosPlayer1 = 0
    var puntosPlayer2 = 0
    var mensaje = "Partida en curso"

    for ((index, jugada) in jugadas.withIndex()) {
        val (j1, j2) = jugada

        if (j1 == j2) {
            mensaje = "Empate en la ronda ${index + 1}"
        } else if ((j1 == "R" && j2 == "S") || (j1 == "S" && j2 == "P") || (j1 == "P" && j2 == "R")) {
            puntosPlayer1++
            mensaje = "Player 1 gana la ronda ${index + 1}"
        } else {
            puntosPlayer2++
            mensaje = "Player 2 gana la ronda ${index + 1}"
        }

        // Verificar si alguien ganó el juego (2 puntos)
        if (puntosPlayer1 == 2) return "Player 1 gana el juego"
        if (puntosPlayer2 == 2) return "Player 2 gana el juego"
    }

    return mensaje
}
