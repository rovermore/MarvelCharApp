package com.example.marvelcharapp.domain.character.repository

import androidx.paging.PagingData
import com.example.marvelcharapp.domain.base.Error
import com.example.marvelcharapp.domain.base.OperationResult
import com.example.marvelcharapp.domain.character.model.CharacterDTO
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacterList(): Flow<PagingData<CharacterDTO>>
    fun getCharacter(id: String): OperationResult<CharacterDTO, Error>
}