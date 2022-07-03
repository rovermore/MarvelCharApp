package com.example.marvelcharapp.domain.character.model

data class CharacterDTO(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val image: MarvelImageDTO
)

data class MarvelImageDTO(
    val path: String = "",
    val extension: String = ""
)