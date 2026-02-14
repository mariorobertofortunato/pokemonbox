package com.example.pokemonbox.domain.usecase

import com.example.pokemonbox.domain.model.PokemonListResult
import com.example.pokemonbox.domain.model.Result
import com.example.pokemonbox.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    operator fun invoke(offset: Int, limit: Int): Flow<Result<PokemonListResult>> =
        repository.fetchPokemonList(offset, limit)
}
