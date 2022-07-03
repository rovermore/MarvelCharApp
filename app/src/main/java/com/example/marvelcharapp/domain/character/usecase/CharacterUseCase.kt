package com.example.marvelcharapp.domain.character.usecase

import com.example.marvelcharapp.domain.base.Error
import com.example.marvelcharapp.domain.base.OperationResult
import com.example.marvelcharapp.domain.character.model.CharacterDTO
import com.example.marvelcharapp.domain.character.repository.CharacterRepository
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    fun getCharacterList(offset: Int): OperationResult<List<CharacterDTO>, Error> {
        return characterRepository.getCharacterList()
    }
}