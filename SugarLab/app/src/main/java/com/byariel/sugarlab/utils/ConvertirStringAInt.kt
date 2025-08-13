package com.byariel.sugarlab.utils

fun ConverrtirStringToInt(texto: String, valorPorDefecto: Int = 0): Int {
    return try {
        texto.toInt()
    } catch (e: NumberFormatException) {
        valorPorDefecto
    }
}


