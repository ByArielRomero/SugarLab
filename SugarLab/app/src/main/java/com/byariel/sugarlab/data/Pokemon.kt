package com.byariel.sugarlab.data

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
enum class TipoPokemon {
    AGUA,
    FUEGO,
    PLANTA,
    ELECTRICO
}

data class Pokemon(
    val nombre: String,
    val tipo: TipoPokemon,
    val ataque: Int,    // ejemplo: 1-100
    val defensa: Int ,   // ejemplo: 1-100
    val imagenUrl: String
)

val pokemones = listOf(
    Pokemon("Squirtle", TipoPokemon.AGUA, ataque = 48, defensa = 65,"https://pngimg.com/uploads/pokemon/pokemon_PNG5.png"),
    Pokemon("Psyduck", TipoPokemon.AGUA, ataque = 52, defensa = 48,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/054.png"),
    Pokemon("Poliwag", TipoPokemon.AGUA, ataque = 50, defensa = 40,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/060.png"),
    Pokemon("Magikarp", TipoPokemon.AGUA, ataque = 10, defensa = 55,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/129.png"),

    Pokemon("Charmander", TipoPokemon.FUEGO, ataque = 52, defensa = 43,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/004.png"),
    Pokemon("Vulpix", TipoPokemon.FUEGO, ataque = 41, defensa = 40,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/037.png"),
    Pokemon("Growlithe", TipoPokemon.FUEGO, ataque = 70, defensa = 45,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/058.png"),
    Pokemon("Ponyta", TipoPokemon.FUEGO, ataque = 85, defensa = 55,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/077.png"),

    Pokemon("Bulbasaur", TipoPokemon.PLANTA, ataque = 49, defensa = 49,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/001.png"),
    Pokemon("Oddish", TipoPokemon.PLANTA, ataque = 50, defensa = 55,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/043.png"),
    Pokemon("Bellsprout", TipoPokemon.PLANTA, ataque = 75, defensa = 35,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/069.png"),
    Pokemon("Exeggcute", TipoPokemon.PLANTA, ataque = 40, defensa = 80,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/102.png"),

    Pokemon("Pikachu", TipoPokemon.ELECTRICO, ataque = 55, defensa = 40,"https://pngimg.com/d/pokemon_PNG140.png"),
    Pokemon("Magnemite", TipoPokemon.ELECTRICO, ataque = 35, defensa = 70,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/081.png"),
    Pokemon("Voltorb", TipoPokemon.ELECTRICO, ataque = 30, defensa = 50,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/100.png"),
    Pokemon("Electabuzz", TipoPokemon.ELECTRICO, ataque = 83, defensa = 57,"https://www.pokemon.com/static-assets/content-assets/cms2/img/pokedex/full/125.png"),

)


fun getGradientByTipo(tipo: TipoPokemon): Brush {
    return when (tipo) {
        TipoPokemon.AGUA -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFF2196F3), // azul intenso
                Color(0xFF90CAF9)  // azul claro suave
            )
        )
        TipoPokemon.FUEGO -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFFD32F2F), // rojo oscuro
                Color(0xFFFFB74D)  // naranja claro
            )
        )
        TipoPokemon.PLANTA -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFF388E3C), // verde oscuro
                Color(0xFFA5D6A7)  // verde claro pastel
            )
        )
        TipoPokemon.ELECTRICO -> Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFBC02D), // amarillo fuerte
                Color(0xFFFFF59D)  // amarillo pálido
            )
        )
    }
}
