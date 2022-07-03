package com.example.marvelcharapp.data.character.model

import com.example.marvelcharapp.domain.character.model.CharacterDTO
import com.example.marvelcharapp.domain.character.model.MarvelImageDTO
import javax.inject.Inject

class CharacterResponseMapper @Inject constructor() {
    fun map(characterResponse: CharacterResponse): CharacterDTO {
        with(characterResponse) {
            return CharacterDTO(
                id = id ?: 0,
                name =  name ?: "",
                description = description ?: "",
                image = MarvelImageDTO(
                    path = image?.path ?: "",
                    extension = image?.extension ?: ""
                )
            )
        }
    }

    fun mapList(catalogResponse: List<CharacterResponse>?): List<CharacterDTO> {
        val characterDTOList = mutableListOf<CharacterDTO>()
        catalogResponse?.let{ catalogResponse ->
            catalogResponse.forEach {
                characterDTOList.add(map(it))
            }
        }
        return characterDTOList
    }


}