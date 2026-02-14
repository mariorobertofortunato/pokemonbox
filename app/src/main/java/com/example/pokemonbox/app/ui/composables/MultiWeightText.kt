package com.example.pokemonbox.app.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MultiWeightText(
    regularTexValue: String,
    boldTextValue: String,
    style: TextStyle = MaterialTheme.typography.titleLarge
){
    Row(){
        Text(
            text = regularTexValue,
            fontWeight = FontWeight.Normal,
            style = style
        )
        Text(
            text = boldTextValue,
            fontWeight = FontWeight.Bold,
            style = style
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MultiWeightTextPreview() {
    MultiWeightText(
        regularTexValue = "Pokémon",
        boldTextValue = "Box"
    )
}