package com.byariel.sugarlab.logic

/*
*   INPUT --> AÑO(INT)
*
*   PROCESS -->
*       *PARA CALCULAR EL ANIMAL--> [(anio - 1984) % 12]
*       *PARA CALCULAR EL ELEMENTO--> [((anio - 1984) % 10) / 2]
*   REGLAS-->
*            *CADA ELEMENTO SE REPITE 2 AÑOS SEGUIDOS
*            *EL ULTIMO CICLO SEXAGENARIO COMENZO EN 1984
*
*   OUTPUT --> ZODIACO(STRING)
*
*
* */


import com.byariel.sugarlab.R

// NUEVO: clase que agrupa toda la info del signo
data class SignoChino(
    val elemento: String,
    val animal: String,
    val imagenRes: Int,
    val caracteristicas: String
)

fun SexagenarioChinoLogic(anio: Int): SignoChino {
    val animals = listOf(
        "Rata", "Buey", "Tigre", "Conejo", "Dragón", "Serpiente",
        "Caballo", "Oveja", "Mono", "Gallo", "Perro", "Cerdo"
    )

    val elements = listOf("Madera", "Fuego", "Tierra", "Metal", "Agua")

    val animalIndex = (anio - 1984).mod(12)
    val elementIndex = ((anio - 1984).mod(10)) / 2

    val animal = animals[animalIndex]
    val elemento = elements[elementIndex]

    val imagenes = listOf(
        R.drawable.rata, R.drawable.buey, R.drawable.tigre, R.drawable.conejo,
        R.drawable.dragon, R.drawable.serpiente, R.drawable.caballo, R.drawable.oveja,
        R.drawable.mono, R.drawable.gallo, R.drawable.perro, R.drawable.cerdo
    )

    val caracteristicasMap = mapOf(
        "Rata" to "Inteligente, adaptable, perspicaz.",
        "Buey" to "Paciente, trabajador, confiable.",
        "Tigre" to "Valiente, enérgico, impulsivo.",
        "Conejo" to "Amable, elegante, prudente.",
        "Dragón" to "Fuerte, ambicioso, carismático.",
        "Serpiente" to "Sabia, misteriosa, intuitiva.",
        "Caballo" to "Libre, apasionado, sociable.",
        "Oveja" to "Creativa, amable, compasiva.",
        "Mono" to "Ingenioso, divertido, curioso.",
        "Gallo" to "Honesto, puntual, observador.",
        "Perro" to "Leal, protector, amigable.",
        "Cerdo" to "Generoso, tolerante, honesto."
    )

    return SignoChino(
        elemento = elemento,
        animal = animal,
        imagenRes = imagenes[animalIndex],
        caracteristicas = caracteristicasMap[animal] ?: ""
    )
}
