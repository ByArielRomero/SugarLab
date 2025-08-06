package com.byariel.sugarlab.logic

// Esta función evalúa si el atleta hizo bien la carrera
fun carreraDeObstaculosLogic(acciones: Array<String>, pista: String): Pair<String, Boolean> {
    val resultado = CharArray(pista.length)  // guarda cómo quedó la pista
    var pasoBien = true                      // al principio, asumimos que todo va bien

    for (i in acciones.indices) {
        val accion = acciones[i]
        val tramo = pista[i]

        // Si hace bien el paso
        if (accion == "run" && tramo == '_') {
            resultado[i] = '_'  // lo deja igual
        } else if (accion == "jump" && tramo == '|') {
            resultado[i] = '|'  // también está bien
        } else if (accion == "jump" && tramo == '_') {
            resultado[i] = 'x'  // saltó mal (error)
            pasoBien = false
        } else if (accion == "run" && tramo == '|') {
            resultado[i] = '/'  // corrió mal (error)
            pasoBien = false
        } else {
            resultado[i] = '?'  // caso raro (error también)
            pasoBien = false
        }
    }

    return String(resultado) to pasoBien  // devolvemos la pista final y si ganó o no
}

// Esta función genera una pista al azar con '_' o '|'
fun generarPistaAleatoria(tamanio: Int): String {
    val opciones = arrayOf('_', '|')   // Dos opciones para la pista
    var pista = ""                     // Empieza vacía

    for (i in 1..tamanio) {
        // Elegimos un índice aleatorio 0 o 1
        val indice = (0..1).random()
        // Le agregamos a la pista el caracter elegido
        pista += opciones[indice]
    }

    return pista  // Devuelve la pista generada
}

// Esta función genera acciones al azar: "run" o "jump"
fun generarAccionesAleatorias(tamanio: Int): Array<String> {
    val opciones = arrayOf("run", "jump")  // Opciones array pre definido
    val acciones = Array(tamanio) { "" }   // Array vacío para llenar

    for (i in 0 until tamanio) {
        val indice = (0..1).random()       // Aleatorio 0 o 1
        acciones[i] = opciones[indice]     // Asignamos run o jump
    }

    return acciones
}

