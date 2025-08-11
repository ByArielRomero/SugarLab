package com.byariel.sugarlab.logic

import com.byariel.sugarlab.model.Raza

object AnillosDelPoderLogic {

    /**
     * Calcula la fuerza total de un ejército.
     * @param ejercito Map con raza y cantidad de integrantes.
     * @return Fuerza total (valor * cantidad).
     */
    fun calcularFuerza(ejercito: Map<Raza, Int>): Int {
        return ejercito.entries.sumOf { (raza, cantidad) ->
            val cantidadValida = cantidad.coerceAtLeast(0) // no negativo
            raza.valor * cantidadValida
        }
    }

    /**
     * Determina el ganador entre dos ejércitos.
     * @param ejercitoBien Map con raza y cantidad.
     * @param ejercitoMal Map con raza y cantidad.
     * @return Texto con resultado: "El bien gana", "El mal gana" o "Empate".
     */
    fun determinarGanador(
        ejercitoBien: Map<Raza, Int>,
        ejercitoMal: Map<Raza, Int>
    ): String {
        val fuerzaBien = calcularFuerza(ejercitoBien)
        val fuerzaMal = calcularFuerza(ejercitoMal)

        return when {
            fuerzaBien > fuerzaMal -> "El bien gana la batalla"
            fuerzaMal > fuerzaBien -> "El mal gana la batalla"
            else -> "Empate en la batalla"
        }
    }
}

