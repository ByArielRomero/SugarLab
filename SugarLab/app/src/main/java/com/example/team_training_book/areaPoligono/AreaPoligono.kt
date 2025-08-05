package com.example.team_training_book.areaPoligono

fun main() {
    // Acá estamos imprimiendo el área de un cuadrado con lado 4
    println("Área cuadrado: ${calcularArea("cuadrado", 5.0)}")

    // Ahora imprimimos el área de un triángulo con base 10 y altura 5
    println("Área triángulo: ${calcularArea("triangulo", 13.0, 6.0)}")

    // Y finalmente, el área de un rectángulo con base 6 y altura 3
    println("Área rectángulo: ${calcularArea("rectangulo", 6.0, 3.0)}")
}

// Esta función calcula el área según el tipo de polígono y las medidas que le pasamos
fun calcularArea(tipo: String, lado1: Double, lado2: Double = 0.0): Double {
    // Usamos 'when' para elegir la fórmula según el tipo que nos dieron (en minúsculas para que no importe mayúscula)
    return when (tipo.lowercase()) {
        // Si es cuadrado, multiplicamos lado por lado (lado1 * lado1)
        "cuadrado" -> lado1 * lado1

        // Si es triángulo, usamos la fórmula (base * altura) / 2, donde lado1 = base y lado2 = altura
        "triangulo" -> (lado1 * lado2) / 2

        // Si es rectángulo, multiplicamos base por altura (lado1 * lado2)
        "rectangulo" -> lado1 * lado2

        // Si no es ninguno de esos, devolvemos 0 porque no sabemos calcularlo
        else -> 0.0
    }
}
