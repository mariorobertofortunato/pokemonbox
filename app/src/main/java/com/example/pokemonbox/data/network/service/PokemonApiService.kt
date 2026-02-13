package com.example.pokemonbox.data.network.service

import com.example.pokemonbox.data.network.model.PokemonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun searchPokemon(
        @Query("search") searchTerm: String
    ) : Response<List<PokemonModel>>

}