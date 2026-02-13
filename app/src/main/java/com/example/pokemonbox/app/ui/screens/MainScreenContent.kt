package com.example.pokemonbox.app.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreenContent(
    modifier: Modifier = Modifier,
) {

    Text(
        text = "Main Screen",
        modifier = modifier

    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenContentPreview() {
    MainScreenContent()
}