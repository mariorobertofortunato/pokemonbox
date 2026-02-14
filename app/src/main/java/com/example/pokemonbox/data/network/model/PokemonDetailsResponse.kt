package com.example.pokemonbox.data.network.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailsResponse(
    val id: Int,
    val name: String,
    val types: List<TypeInfo>,
    val sprites: Sprites
)

data class TypeInfo(
    val slot: Int,
    val type: Type
)

data class Type(
    val name: String
)

data class Sprites(
    @SerializedName("other") val other: OtherSprites
)

data class OtherSprites(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtwork
)

data class OfficialArtwork(
    @SerializedName("front_default") val frontDefault: String?
)