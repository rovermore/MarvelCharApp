package com.example.marvelcharapp

import com.example.marvelcharapp.domain.character.model.CharacterDTO
import com.example.marvelcharapp.domain.character.model.MarvelImageDTO

object CharacterMock {

    val characterDTO1 = CharacterDTO(
        1,
        "character1",
        "this is the character 1",
        MarvelImageDTO("path1", "extension1")
    )

    val characterDTO2 = CharacterDTO(
        2,
        "character2",
        "this is the character 2",
        MarvelImageDTO("path2", "extension2")
    )

    val characterListDTO = arrayListOf(characterDTO1, characterDTO2)
}