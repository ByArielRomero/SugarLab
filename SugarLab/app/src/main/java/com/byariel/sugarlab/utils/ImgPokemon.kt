package com.byariel.sugarlab.utils


fun getPokemonImageUrl(pokemonUrl: String): String {
    val id = pokemonUrl.trimEnd('/').takeLastWhile { it.isDigit() }
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}
