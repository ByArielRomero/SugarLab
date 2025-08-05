package com.example.team_training_book.fizzBuzz


fun main() {
    // Recorremos los números del 1 al 100 (ambos inclusive)
    for (num in 1..100) {
        // Si el número es múltiplo de 3 y de 5 (o sea, de 15), imprimimos "fizzbuzz"
        if (num % 3 == 0 && num % 5 == 0) {
            println("fizzbuzz")
        }
        // Si sólo es múltiplo de 3, imprimimos "fizz"
        else if (num % 3 == 0) {
            println("fizz")
        }
        // Si sólo es múltiplo de 5, imprimimos "buzz"
        else if (num % 5 == 0) {
            println("buzz")
        }
        // Si no es múltiplo ni de 3 ni de 5, imprimimos el número tal cual
        else {
            println(num)
        }
    }
}

