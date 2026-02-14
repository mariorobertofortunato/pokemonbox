package com.example.pokemonbox.app.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.pokemonbox.domain.model.Pokemon

@Composable
fun PokemonEntryThumbnail(
    entry: Pokemon
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                    RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            if (entry.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = entry.imageUrl,
                    contentDescription = entry.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // name
            Text(
                text = entry.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            // types list
            if (entry.types.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ){
                    entry.types.forEach {
                        TypeTag(value = it)
                    }
                }
            }

            // desc
            if (entry.description.isNotEmpty()) {
                Text(
                    text = entry.description,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PokemonEntryThumbnailPreview() {
    PokemonEntryThumbnail(
        entry = Pokemon(
            id = 1,
            name = "Bulbasaur",
            types = listOf("Grass", "Poison"),
            description = "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon."
        )
    )
}