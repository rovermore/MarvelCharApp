package com.example.marvelcharapp.data.character

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelcharapp.data.base.APIErrorMapper
import com.example.marvelcharapp.data.character.model.CharacterResponseMapper
import com.example.marvelcharapp.data.character.network.CharacterNetworkDatasource
import com.example.marvelcharapp.data.character.network.CharacterPagingSource
import com.example.marvelcharapp.domain.base.*
import com.example.marvelcharapp.domain.character.model.CharacterDTO
import com.example.marvelcharapp.domain.character.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val characterNetworkDatasource: CharacterNetworkDatasource,
    private val apiErrorMapper: APIErrorMapper,
    private val characterResponseMapper: CharacterResponseMapper
): CharacterRepository {

    override suspend fun getCharacterList(): Flow<PagingData<CharacterDTO>> {
        return Pager(
            config = PagingConfig(
                pageSize = CharacterPagingSource.PAGE_SIZE,
                prefetchDistance = 25,
                initialLoadSize = 30
            ),
            pagingSourceFactory = {
                CharacterPagingSource(
                    characterNetworkDatasource, characterResponseMapper, apiErrorMapper
                )
            }
        ).flow
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