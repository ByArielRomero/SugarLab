package com.byariel.sugarlab.network

import com.byariel.sugarlab.network.response.PokemonResponse
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonService {
        @GET("pokemon")
        suspend fun getPokemons(@Query("limit") limit: Int = 20): PokemonResponse

}