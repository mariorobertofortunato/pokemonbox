package com.example.pokemonbox.data.network.model

import com.google.gson.annotations.SerializedName

data class PokemonSpeciesResponse(
    @SerializedName("flavor_text_entries") val flavorTextEntries: List<FlavorTextEntry>
)

data class FlavorTextEntry(
    @SerializedName("flavor_text") val flavorText: String,
    val language: LanguageRef,
    val version: VersionRef? = null
)

data class LanguageRef(
    val name: String
)

data class VersionRef(
    val name: String
)
