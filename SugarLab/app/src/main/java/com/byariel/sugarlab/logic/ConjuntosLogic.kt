package com.byariel.sugarlab.logic

fun ConjuntosLogic(array1: Array<Int>, array2: Array<Int>, esComun: Boolean): Array<Int> {
    // Creamos una lista mutable para guardar el resultado (elementos comunes o no comunes)
    val resultado = mutableListOf<Int>()

    // Si esComun es verdadero, buscamos los elementos que están en los dos arrays
    if (esComun) {
        // Recorremos todos los elementos de array1
        for (e1 in array1) {
            // Por cada elemento de array1, recorremos todo array2
            for (e2 in array2) {
                // Si el elemento de array1 también está en array2
                if (e1 == e2 && !resultado.contains(e1)) {
                    // Lo agregamos al resultado solo si aún no está (para evitar duplicados)
                    resultado.add(e1)
                }
            }
        }

    } else {
        // Si esComun es falso, buscamos los elementos NO comunes

        // 1. Elementos de array1 que NO están en array2
        for (e1 in array1) {
            var esta = false // Bandera para saber si se repite
            for (e2 in array2) {
                if (e1 == e2) {
                    esta = true // Si lo encontramos, marcamos como encontrado
                    break        // Ya no hace falta seguir buscando
                }
            }
            if (!esta && !resultado.contains(e1)) {
                resultado.add(e1) // Si NO estaba en array2, lo agregamos
            }
        }

        // 2. Elementos de array2 que NO están en array1 (repetimos lógica al revés)
        for (e2 in array2) {
            var esta = false
            for (e1 in array1) {
                if (e2 == e1) {
                    esta = true
                    break
                }
            }
            if (!esta && !resultado.contains(e2)) {
                resultado.add(e2)
            }
        }
    }

    //Convertimos la lista mutable a un Array y lo devolvemos
            return resultado.toTypedArray()
}
