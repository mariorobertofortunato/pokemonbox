package com.example.pokemonbox.domain.model

data class PokemonList(
    val list: List<Pokemon>,
    val hasNextPage: Boolean
)
