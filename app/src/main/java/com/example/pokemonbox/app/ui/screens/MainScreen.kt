package com.example.pokemonbox.app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pokemonbox.app.ui.composables.PokeTopBar

@Composable
fun MainScreen (){

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        topBar = {
            PokeTopBar()
        },
        bottomBar = {
            // TODO: add bottom bar
        }
    ) { paddingValues ->
        MainScreenContent(
            modifier = Modifier
                .padding(paddingValues)
        )
    }

}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}