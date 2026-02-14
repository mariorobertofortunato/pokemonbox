package com.example.pokemonbox.data.network.service

import com.example.pokemonbox.data.network.model.PokemonDetailsResponse
import com.example.pokemonbox.data.network.model.PokemonListResponse
import com.example.pokemonbox.data.network.model.PokemonSpeciesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonListResponse>

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetails(
        @Path("id") id: Int
    ): Response<PokemonDetailsResponse>

 //descrizione
    @GET("pokemon-species/{id}")
    suspend fun fetchPokemonSpecies(
        @Path("id") id: Int
    ): Response<PokemonSpeciesResponse>
}