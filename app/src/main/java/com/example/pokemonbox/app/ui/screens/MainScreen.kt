package com.example.pokemonbox.app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.pokemonbox.app.ui.composables.PokeTopBar
import com.example.pokemonbox.app.viewmodel.MainScreenViewModel

@Composable
fun MainScreen(
    mainScreenViewModel: MainScreenViewModel = hiltViewModel()
) {
    val searchQuery by mainScreenViewModel.searchQuery.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        topBar = {
            PokeTopBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding(),
                searchQuery = searchQuery,
                onSearchQueryChange = mainScreenViewModel::updateSearchQuery
            )
        },
        bottomBar = {
            // TODO: add bottom bar
        }
    ) { paddingValues ->
        MainScreenContent(
            modifier = Modifier
                .padding(paddingValues),
            searchQuery = searchQuery,
        )
    }

}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}