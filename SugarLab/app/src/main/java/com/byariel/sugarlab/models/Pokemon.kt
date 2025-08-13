package com.byariel.sugarlab.models

data class Pokemon(
        val name: String,
        val url: String,
        val hp: Int = (50..100).random(),
        val ataque: Int = (50..100).random(),
        val defensa: Int = (30..80).random()
)
