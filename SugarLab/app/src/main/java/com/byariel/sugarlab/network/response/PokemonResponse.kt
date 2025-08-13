package com.byariel.sugarlab.network.response

import com.byariel.sugarlab.models.Pokemon

data class PokemonResponse(
    val results: List<Pokemon>
)
