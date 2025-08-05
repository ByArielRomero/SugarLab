package com.byariel.sugarlab.logic

fun FizzBuzzEvaluar(num: Int):String{
    return when {
        num % 3 == 0 && num % 5 == 0 -> "🥳 ¡FizzBuzz!"
        num % 3 == 0 -> "Fizz 🍋"
        num % 5 == 0 -> "Buzz 🍭"
        else -> "🔸 $num no es múltiplo de 3 ni de 5"
    }
}