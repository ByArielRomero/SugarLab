package com.byariel.sugarlab.logic

/*
* * Enunciado: Calcula dónde estará un robot (sus
coordenadas finales) que se encuentra en una cuadrícula
*  representada por los ejes "x" e "y".
*
*   0   1   2   3   4   5
* 0 |x|| | | | | | | | | |
* 0 | || | | | | | | | | |
* 0 | || | | | | | | | | |
* 0 | || | | | | | | | | |
*
* MOVIMIENTO --> [1,3,-5,3,2]
*
* - El robot comienza en la coordenada (0, 0).
13* - Para idicarle que se mueva, le enviamos un array
formado por enteros (positivos o negativos)
*
que indican la secuencia de pasos a dar.
* - Por ejemplo: [10, 5, -2] indica que primero se mueve
10 pasos, se detiene, luego 5, se detiene,
*
y finalmente 2. El resultado en este caso sería (x:
-5, y: 12)
* - Si el número de pasos es negativo, se desplazaría en
sentido contrario al que está mirando.
* - Los primeros pasos los hace en el eje "y".
Interpretamos que está mirando hacia la parte
*
positiva del eje "y".
* - El robot tiene un fallo en su programación: cada vez
que finaliza una secuencia de pasos gira
*
90 grados en el sentido contrario a las agujas del
reloj.
*
*
*
*
*
*
* */


// Direcciones del robot

// Direcciones del robot (90° contrarreloj cada vez)
enum class Direccion {
    Y_POSITIVO, X_NEGATIVO, Y_NEGATIVO, X_POSITIVO
}

// Calcula la posición final del robot (X,Y)
fun calcularPosicionFinal(pasos: List<Int>): Pair<Int, Int> {
    var x = 0
    var y = 0
    var direccionActual = Direccion.Y_POSITIVO

    for (paso in pasos) {
        when (direccionActual) {
            Direccion.Y_POSITIVO -> y += paso
            Direccion.X_NEGATIVO -> x -= paso
            Direccion.Y_NEGATIVO -> y -= paso
            Direccion.X_POSITIVO -> x += paso
        }

        direccionActual = when (direccionActual) {
            Direccion.Y_POSITIVO -> Direccion.X_NEGATIVO
            Direccion.X_NEGATIVO -> Direccion.Y_NEGATIVO
            Direccion.Y_NEGATIVO -> Direccion.X_POSITIVO
            Direccion.X_POSITIVO -> Direccion.Y_POSITIVO
        }
    }

    return Pair(x, y) // X primero, Y segundo
}
