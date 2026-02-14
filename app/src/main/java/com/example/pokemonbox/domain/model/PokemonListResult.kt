package com.example.pokemonbox.domain.model

data class PokemonListResult(
    val list: List<Pokemon>,
    val hasNextPage: Boolean
)
