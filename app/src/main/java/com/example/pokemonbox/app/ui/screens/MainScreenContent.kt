package com.example.pokemonbox.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.pokemonbox.R
import com.example.pokemonbox.app.ui.composables.PokemonEntryThumbnail
import com.example.pokemonbox.app.viewmodel.MainScreenUiState
import com.example.pokemonbox.app.viewmodel.MainScreenViewModel

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {

    val mainScreenUiState by mainScreenViewModel.mainScreenUiState.collectAsState()

    LaunchedEffect(Unit) {
        mainScreenViewModel.loadPokemonList()
    }

    when (val state = mainScreenUiState) {

        is MainScreenUiState.Error -> {

            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = state.message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                TextButton(
                    onClick = {
                        mainScreenViewModel.loadPokemonList()
                    },
                    content = {
                        Text(
                            text = stringResource(R.string.retry)
                        )
                    }
                )
            }
        }

        is MainScreenUiState.Success -> {
            val entries = remember(state.entriesList, searchQuery) {
                val query = searchQuery.trim().lowercase()
                if (query.isEmpty()) {
                    state.entriesList
                } else {
                    state.entriesList.filter { pokemon ->
                        pokemon.name.lowercase().contains(query) ||
                                pokemon.types.any { it.lowercase().contains(query) }
                    }
                }
            }
            val listState = rememberLazyListState()
            LaunchedEffect(listState) {
                snapshotFlow {
                    val layoutInfo = listState.layoutInfo
                    layoutInfo.visibleItemsInfo.lastOrNull()?.index to layoutInfo.totalItemsCount
                }.collect { (lastVisible, total) ->
                    if (lastVisible != null) {
                        if (total > 0 && lastVisible >= total - 2) {
                            mainScreenViewModel.loadPokemonList()
                        }
                    }
                }
            }
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier.fillMaxWidth()
            ) {
                itemsIndexed(
                    items = entries,
                    key = { index, entry -> entry.id }
                ) { index, entry ->

                    // Pokemonn card
                    PokemonEntryThumbnail(entry = entry)
                    // Divider
                    if (index != entries.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(top = 16.dp),
                            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.25f)
                        )
                    }
                }
                if (state.isLoading) {
                    item(key = "loading") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    if (state.entriesList.isEmpty()) Modifier.fillParentMaxHeight()
                                    else Modifier.padding(24.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        else -> {}
    }
}