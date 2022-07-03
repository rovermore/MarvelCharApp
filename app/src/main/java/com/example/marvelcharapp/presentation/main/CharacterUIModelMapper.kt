package com.example.marvelcharapp.presentation.main

import com.example.marvelcharapp.domain.character.model.CharacterDTO
import javax.inject.Inject

class CharacterUIModelMapper @Inject constructor() {

    fun map(characterDTO: CharacterDTO): CharacterUIModel =
        CharacterUIModel(
            id = characterDTO.id,
            name = characterDTO.name,
            description = characterDTO.description,
            image = MarvelImageUrl(
                characterDTO.image.path,
                characterDTO.image.extension
            )
        )

    fun mapList(characterDTOList: List<CharacterDTO>): List<CharacterUIModel> {
        val characterUIList = mutableListOf<CharacterUIModel>()
        characterDTOList.forEach {
            characterUIList.add(map(it))
        }
        return characterUIList.toList()
    }
}