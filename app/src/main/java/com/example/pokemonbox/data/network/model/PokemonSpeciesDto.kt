package com.example.pokemonbox.data.network.model

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesDto(
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntryDto>
)

data class FlavorTextEntryDto(
    @SerializedName("flavor_text") val flavorText: String,
    @SerializedName("language") val language: LanguageRefDto,
    @SerializedName("version") val version: VersionRefDto? = null
)

data class LanguageRefDto(
    @SerializedName("name") val name: String
)

data class VersionRefDto(
    @SerializedName("name") val name: String
)
