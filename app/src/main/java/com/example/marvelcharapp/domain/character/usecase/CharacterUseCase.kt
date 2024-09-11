package com.example.marvelcharapp.domain.character.usecase

import androidx.paging.PagingData
import com.example.marvelcharapp.domain.base.Error
import com.example.marvelcharapp.domain.base.OperationResult
import com.example.marvelcharapp.domain.character.model.CharacterDTO
import com.example.marvelcharapp.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    suspend fun getCharacterList(): Flow<PagingData<CharacterDTO>> {
        return characterRepository.getCharacterList()
    }

    fun getCharacter(id: String): OperationResult<CharacterDTO, Error> {
        return characterRepository.getCharacter(id)
    }
}