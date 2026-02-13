package com.example.pokemonbox.app.ui.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokeTopBar (
    modifier: Modifier = Modifier
) {
    Text(
        text = "PokéBox",
        modifier = modifier
    )

}

@Preview
@Composable
fun PokeTopBarPreview () {
    PokeTopBar()
}
