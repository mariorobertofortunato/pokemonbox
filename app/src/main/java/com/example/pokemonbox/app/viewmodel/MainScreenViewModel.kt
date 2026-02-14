package com.example.pokemonbox.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonbox.domain.model.Pokemon
import com.example.pokemonbox.domain.model.Result
import com.example.pokemonbox.domain.usecase.GetPokemonListUseCase
import com.example.pokemonbox.utils.Constants.PAGE_LIMIT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
) : ViewModel() {

    private val _mainScreenUiState = MutableStateFlow<MainScreenUiState>(MainScreenUiState.Idle)
    val mainScreenUiState: StateFlow<MainScreenUiState> = _mainScreenUiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun loadPokemonList() {
        when (val state = _mainScreenUiState.value) {
            is MainScreenUiState.Success -> {
                if (state.isLoading) return
                if (!state.hasNextPage) return
                _mainScreenUiState.value = state.copy(isLoading = true)
                viewModelScope.launch {
                    loadPage(state.currentPage + 1)
                }
            }
            else -> { // Idle o Error: carica prima pagina
                _mainScreenUiState.value = MainScreenUiState.Success(isLoading = true)
                viewModelScope.launch {
                    loadPage(0)
                }
            }
        }
    }

    private suspend fun loadPage(page: Int) {
        val currentState = _mainScreenUiState.value as? MainScreenUiState.Success
        if (page > 0 && currentState != null && currentState.currentPage >= page) return
        val currentList = currentState?.entriesList ?: emptyList()
        val isFirstPage = page == 0

        getPokemonListUseCase(offset = page * PAGE_LIMIT, limit = PAGE_LIMIT)
            .catch { e ->
                val message = e.message ?: "Errore di connessione"
                if (isFirstPage) {
                    _mainScreenUiState.value = MainScreenUiState.Error(message)
                } else {
                    _mainScreenUiState.value = currentState?.copy(isLoading = false) ?: MainScreenUiState.Error(message)
                }
            }
            .collect { result ->
                when (result) {
                    is Result.Error -> {
                        if (isFirstPage) {
                            _mainScreenUiState.value = MainScreenUiState.Error(result.message)
                        } else {
                            _mainScreenUiState.value = currentState?.copy(isLoading = false) ?: MainScreenUiState.Error(result.message)
                        }
                    }
                    is Result.Success -> {
                        val result = result.data
                        val newList = if (isFirstPage) result.list else currentList + result.list
                        _mainScreenUiState.value = MainScreenUiState.Success(
                            entriesList = newList,
                            hasNextPage = result.hasNextPage,
                            currentPage = page,
                            isLoading = false
                        )
                    }
                    else -> {}
                }
            }
    }
}

sealed class MainScreenUiState {
    object Idle : MainScreenUiState()
    data class Error(val message: String) : MainScreenUiState()
    data class Success(
        val entriesList: List<Pokemon> = emptyList(),
        val hasNextPage: Boolean = false,
        val currentPage: Int = 0,
        val isLoading: Boolean = false
    ) : MainScreenUiState()

    data class SelectedPokemon(val selectedEntry: Pokemon) : MainScreenUiState()
}
