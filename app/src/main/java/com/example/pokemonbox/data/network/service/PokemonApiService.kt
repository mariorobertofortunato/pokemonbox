package com.example.pokemonbox.data.network.service

import com.example.pokemonbox.data.network.model.PokemonDetailsDto
import com.example.pokemonbox.data.network.model.PokemonListDto
import com.example.pokemonbox.data.network.model.PokemonSpeciesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {

    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<PokemonListDto>

    @GET("pokemon/{id}")
    suspend fun fetchPokemonDetails(
        @Path("id") id: Int
    ): Response<PokemonDetailsDto>

 //descrizione (?) // todo capire se esite un campo/endpoint esplicito per la descrizione
    @GET("pokemon-species/{id}")
    suspend fun fetchPokemonSpecies(
        @Path("id") id: Int
    ): Response<PokemonSpeciesDto>
}