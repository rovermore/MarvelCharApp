package com.example.marvelcharapp.domain.character.repository

import com.example.marvelcharapp.domain.base.Error
import com.example.marvelcharapp.domain.base.OperationResult
import com.example.marvelcharapp.domain.character.model.CharacterDTO

interface CharacterRepository {
    fun getCharacterList(offset: Int): OperationResult<List<CharacterDTO>, Error>
    fun getCharacter(id: String): OperationResult<CharacterDTO, Error>
}