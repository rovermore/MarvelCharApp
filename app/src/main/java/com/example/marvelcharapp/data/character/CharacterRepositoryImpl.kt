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

    override suspend fun getCharacterList(offset: Int): OperationResult<List<CharacterDTO>, Error> {
        return characterNetworkDatasource.getCharacterList(offset)
            .map { response ->
                response.data?.let {
                    return Success(characterResponseMapper.mapList(it.results))
                } ?: return Failure(Error.UncompletedOperation("Could not map the character list"))

            }
            .mapFailure {
                return Failure(apiErrorMapper.map(it))
            }
    }

    override fun getCharacter(id: String): OperationResult<CharacterDTO, Error> {
        return characterNetworkDatasource.getCharacter(id)
            .map { response ->
                response.data?.results?.let {
                    return Success(characterResponseMapper.map(it[0]))
                } ?: return Failure(Error.UncompletedOperation("Could not map the character"))
            }
            .mapFailure {
                return Failure(apiErrorMapper.map(it))
            }
    }
}