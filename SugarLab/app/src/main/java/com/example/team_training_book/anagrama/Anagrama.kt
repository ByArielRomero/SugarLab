package com.example.team_training_book.anagrama

import androidx.compose.runtime.sourceInformation

fun main(){
    // Pedimos la primera palabra al usuario
    println("Ingrese la primera palabra:")
    var palabraUno = readLine()

    // Pedimos la segunda palabra
    println("Ingrese la segunda palabra:")
    var palabraDos = readLine()

    // Llamamos a la función anagrama y guardamos el resultado
    // Usamos !! porque asumimos que el usuario sí ingresó algo
    var resultadoAnagrama = anagrama(palabraUno!!, palabraDos!!)

    // Mostramos el resultado según sea verdadero o falso
    if (resultadoAnagrama){
        println("Las palabras ingresadas, son ANAGRAMAS :)")
    } else {
        println("Las palabras ingresadas no son ANAGRAMAS :(")
    }
}

// Función que verifica si dos palabras son anagramas
fun anagrama(palabra1: String, palabra2: String): Boolean{
    // Pasamos la primera palabra a minúsculas para no preocuparnos por mayúsculas
    var palabra1 = palabra1.lowercase()
    // Convertimos la segunda palabra a minúsculas y la transformamos en una lista que podemos modificar
    var letras2 = palabra2.lowercase().toMutableList()

    // Si las palabras son iguales o tienen diferente cantidad de letras, no son anagramas
    if ((palabra1 == palabra2) || (palabra1.length != letras2.size)) return false

    // Recorremos letra por letra la primera palabra
    for (letra in palabra1) {
        // Si la letra está en la lista de letras de la segunda palabra, la "tachamos" (la sacamos)
        if (letra in letras2) {
            letras2.remove(letra)
        } else {
            // Si no está la letra que buscamos, significa que no es anagrama y terminamos
            return false
        }
    }

    // Si después de tachar todas las letras la lista quedó vacía, son anagramas
    return letras2.isEmpty()
}