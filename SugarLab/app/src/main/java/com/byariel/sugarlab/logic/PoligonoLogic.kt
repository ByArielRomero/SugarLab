package com.byariel.sugarlab.logic


fun calcularArea(tipo: String, lado1: Double, lado2: Double = 0.0): Double {
    return when (tipo.lowercase()) {
        "cuadrado" -> lado1 * lado1
        "triangulo" -> (lado1 * lado2) / 2
        "rectangulo" -> lado1 * lado2
        else -> 0.0
    }
}

