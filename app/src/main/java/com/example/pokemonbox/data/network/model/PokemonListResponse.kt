package com.example.pokemonbox.data.network.model

data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<NetworkPokemon>
)

data class NetworkPokemon(
    val name: String,
    val url: String
)
