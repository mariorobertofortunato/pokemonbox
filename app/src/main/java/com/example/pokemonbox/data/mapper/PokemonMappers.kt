package com.example.pokemonbox.data.mapper

import com.example.pokemonbox.data.network.model.PokemonDetailsDto
import com.example.pokemonbox.data.network.model.PokemonListDto
import com.example.pokemonbox.data.network.model.PokemonSpeciesDto
import com.example.pokemonbox.domain.model.Pokemon

/** DTO -> Domain*/
fun PokemonListDto.toDomain(): List<Pokemon> {
    return results.map { item ->
        val id = item.url.split("/").last { it.isNotBlank() }.toInt()
        Pokemon(
            id = id,
            name = item.name.replaceFirstChar { it.uppercase() },
            url = item.url
        )
    }
}

fun PokemonDetailsDto.toDomain(description: String = ""): Pokemon {
    return Pokemon(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        url = "",
        types = types.sortedBy { it.slot }
            .map { it.type.name.replaceFirstChar { c -> c.uppercase() } },
        imageUrl = sprites.other.officialArtwork.frontDefault ?: "",
        description = description
    )
}

fun PokemonSpeciesDto.toDomain(language: String = "en"): String {
    val text = flavorTextEntries
        .firstOrNull { it.language.name == language }
        ?.flavorText
        ?: flavorTextEntries.firstOrNull()?.flavorText
        ?: return ""
    return text.replace("\n", " ").replace("\u000c", " ").replace(Regex("\\s+"), " ").trim()
}
