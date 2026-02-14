package com.example.pokemonbox.data.repository

import com.example.pokemonbox.data.mapper.toDomain
import com.example.pokemonbox.data.network.model.NetworkResponse
import com.example.pokemonbox.data.network.service.PokemonApiService
import com.example.pokemonbox.domain.model.Pokemon
import com.example.pokemonbox.domain.model.PokemonList
import com.example.pokemonbox.domain.model.Result
import com.example.pokemonbox.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApiService: PokemonApiService
) : PokemonRepository {

    override fun fetchPokemonList(offset: Int, limit: Int): Flow<Result<PokemonList>> = flow {
        emit(Result.Loading)
        val result = withContext(Dispatchers.IO) {
            when (val response = handleNetworkResponse {
                pokemonApiService.fetchPokemonList(offset, limit)
            }) {
                is NetworkResponse.Success -> {
                    val listResponse = response.data
                    val pokemonList = listResponse.toDomain()
                    val detailedList = coroutineScope {
                        val semaphore = Semaphore(5)
                        pokemonList.map { pokemon ->
                            async {
                                semaphore.withPermit {
                                    fetchPokemonDetails(pokemon.id)
                                }
                            }
                        }.awaitAll()
                    }.mapIndexed { index, details ->
                        val p = pokemonList[index]
                        details?.copy(url = p.url) ?: Pokemon(
                            id = p.id,
                            name = p.name,
                            url = p.url,
                            types = emptyList(),
                            imageUrl = "",
                            description = ""
                        )
                    }
                    Result.Success(
                        PokemonList(list = detailedList, hasNextPage = listResponse.next != null)
                    )
                }
                is NetworkResponse.Error -> Result.Error(
                    message = response.message ?: "Errore di rete",
                    code = response.code
                )
                is NetworkResponse.Exception -> Result.Error(
                    message = response.throwable.message ?: "Errore di connessione"
                )
            }
        }
        emit(result)
    }

    private suspend fun fetchPokemonDetails(id: Int): Pokemon? =
        withContext(Dispatchers.IO) {
            when (val detailsResponse = handleNetworkResponse {
                pokemonApiService.fetchPokemonDetails(id)
            }) {
                is NetworkResponse.Success -> {
                    val description = when (val speciesResponse = handleNetworkResponse {
                        pokemonApiService.fetchPokemonSpecies(id)
                    }) {
                        is NetworkResponse.Success -> speciesResponse.data.toDomain()
                        else -> ""
                    }
                    detailsResponse.data.toDomain(description)
                }
                is NetworkResponse.Error,
                is NetworkResponse.Exception -> null
            }
        }

    private suspend fun <T : Any> handleNetworkResponse(
        execute: suspend () -> Response<T>
    ): NetworkResponse<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                NetworkResponse.Success(body)
            } else {
                NetworkResponse.Error(code = response.code(), message = response.message())
            }
        } catch (e: HttpException) {
            NetworkResponse.Error(code = e.code(), message = e.message())
        } catch (e: Throwable) {
            NetworkResponse.Exception(e)
        }
    }
}
