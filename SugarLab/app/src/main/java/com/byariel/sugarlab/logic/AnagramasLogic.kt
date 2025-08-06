package com.byariel.sugarlab.logic

import androidx.compose.runtime.MutableState

fun sonAnagramas(palabra1: String, palabra2: String): Boolean {
    val palabra1 = palabra1.lowercase()
    val letras2 = palabra2.lowercase().toMutableList()

    if ((palabra1 == palabra2) || (palabra1.length != letras2.size)) return false

    for (letra in palabra1) {
        if (letra in letras2) {
            letras2.remove(letra)
        } else {
            return false
        }
    }
    return letras2.isEmpty()
}
