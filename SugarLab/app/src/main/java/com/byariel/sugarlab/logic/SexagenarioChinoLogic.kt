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

fun SexagenarioChinoLogic(anio:Int):Pair<String,String>{
    val animals = listOf("Rata","Buey","Tigre","Conejo","Dragón","Serpiente","Caballo","Oveja","Mono","Gallo","Perro","Cerdo")
    val elements = listOf("Madera","Fuego","Tierra","Metal","Agua")

    val animal = animals[(anio - 1984) % 12]
    val element = elements[((anio - 1984) % 10) / 2]

    return element to animal

}


fun main(){
    return println(SexagenarioChinoLogic(1984))
}