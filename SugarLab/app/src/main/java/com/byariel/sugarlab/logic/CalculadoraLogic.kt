package com.byariel.sugarlab.logic


// Esta clase guarda el resultado del cálculo y si fue válido o no
data class ResultadoCalculo(
    val esValido: Boolean,       // true si el archivo es válido y se pudo calcular
    val resultado: Double? = null // resultado numérico si fue válido, sino null
)

// Función que recibe una lista de líneas con números y operadores
// y devuelve si el archivo es válido y el resultado del cálculo
fun CalculadoraLogic(lineas: List<String>): ResultadoCalculo {
    println("Líneas recibidas para validar: $lineas")

    // 1. Primero chequeamos que la lista no esté vacía
    if (lineas.isEmpty()) return ResultadoCalculo(false)

    // 2. La cantidad de líneas debe ser impar
    // porque alternan número, operador, número, operador...
    if (lineas.size % 2 == 0) return ResultadoCalculo(false)

    // 3. Definimos qué operadores están permitidos
    val operadoresValidos = setOf("+", "-", "*", "/")

    // 4. Ahora recorremos las líneas para validar el formato
    for (i in lineas.indices) {
        val linea = lineas[i].trim() // quitamos espacios al inicio y fin

        if (i % 2 == 0) {
            // En posiciones pares (0, 2, 4, ...) debe haber un número
            // Probamos convertir el texto a número (Double)
            if (linea.toDoubleOrNull() == null) return ResultadoCalculo(false)
        } else {
            // En posiciones impares (1, 3, 5, ...) debe haber un operador válido
            if (!operadoresValidos.contains(linea)) return ResultadoCalculo(false)
        }
    }

    // 5. Si pasamos la validación, calculamos el resultado simple
    // Nota: Aquí no respetamos la prioridad de operaciones, calculamos de izquierda a derecha

    var resultado = lineas[0].toDouble() // empezamos con el primer número
    var i = 1

    // Iteramos en pasos de 2: operador y siguiente número
    while (i < lineas.size) {
        val operador = lineas[i].trim()          // operador en posición impar
        val siguienteNumero = lineas[i + 1].toDouble() // número siguiente

        // Aplicamos la operación según el operador
        resultado = when (operador) {
            "+" -> resultado + siguienteNumero
            "-" -> resultado - siguienteNumero
            "*" -> resultado * siguienteNumero
            "/" -> {
                if (siguienteNumero == 0.0) {
                    // Evitamos dividir por cero, devuelve error
                    return ResultadoCalculo(false)
                }
                resultado / siguienteNumero
            }
            else -> return ResultadoCalculo(false) // operador inválido, error
        }
        i += 2 // avanzamos al siguiente operador
    }

    // 6. Devolvemos el resultado calculado y que fue válido
    return ResultadoCalculo(true, resultado)
}
