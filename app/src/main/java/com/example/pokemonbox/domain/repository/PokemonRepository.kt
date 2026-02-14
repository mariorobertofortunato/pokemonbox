package com.example.pokemonbox.domain.repository

import com.example.pokemonbox.domain.model.PokemonList
import com.example.pokemonbox.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun fetchPokemonList(offset: Int, limit: Int): Flow<Result<PokemonList>>
}
