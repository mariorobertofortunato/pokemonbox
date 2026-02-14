package com.example.pokemonbox.data.network.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("types") val types: List<TypeInfoDto>,
    @SerializedName("sprites") val sprites: SpritesDto
)

data class TypeInfoDto(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: TypeDto
)

data class TypeDto(
    @SerializedName("name") val name: String
)

data class SpritesDto(
    @SerializedName("other") val other: OtherSpritesDto
)

data class OtherSpritesDto(
    @SerializedName("official-artwork") val officialArtwork: OfficialArtworkDto
)

data class OfficialArtworkDto(
    @SerializedName("front_default") val frontDefault: String?
)
