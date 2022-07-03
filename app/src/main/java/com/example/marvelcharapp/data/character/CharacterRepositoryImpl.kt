package com.example.marvelcharapp.data.character

import com.example.marvelcharapp.data.base.APIErrorMapper
import com.example.marvelcharapp.data.character.model.CharacterResponseMapper
import com.example.marvelcharapp.data.character.network.CharacterNetworkDatasource
import com.example.marvelcharapp.domain.base.*
import com.example.marvelcharapp.domain.character.model.CharacterDTO
import com.example.marvelcharapp.domain.character.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterNetworkDatasource: CharacterNetworkDatasource,
    private val apiErrorMapper: APIErrorMapper,
    private val characterResponseMapper: CharacterResponseMapper
): CharacterRepository {
    override fun getCharacterList(offset: Int): OperationResult<List<CharacterDTO>, Error> {
        return characterNetworkDatasource.getCharacterList(offset)
            .map {
                return Success(characterResponseMapper.mapList(it.data?.results))
            }
            .mapFailure {
                return Failure(apiErrorMapper.map(it))
            }
    }
}