package com.example.marvelcharapp.data.character.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelcharapp.data.base.APIError
import com.example.marvelcharapp.data.base.APIErrorMapper
import com.example.marvelcharapp.data.character.model.CatalogResponse
import com.example.marvelcharapp.data.character.model.CharacterResponseMapper
import com.example.marvelcharapp.domain.base.Error
import com.example.marvelcharapp.domain.base.get
import com.example.marvelcharapp.domain.character.model.CharacterDTO

class CharacterPagingSource(
    private val characterNetworkDatasource: CharacterNetworkDatasource,
    private val characterResponseMapper: CharacterResponseMapper,
    private val apiErrorMapper: APIErrorMapper,
    ) : PagingSource<Int, CharacterDTO>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 0
        const val PAGE_SIZE = 20
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDTO>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDTO> {
        val currentPage = params.key ?: STARTING_PAGE_INDEX
        return when(val result = characterNetworkDatasource.getCharacterList(currentPage).get()) {
            is CatalogResponse -> LoadResult.Page(
                data = characterResponseMapper.mapList(result.data?.results),
                prevKey = if (currentPage > STARTING_PAGE_INDEX) currentPage.minus(PAGE_SIZE) else null,
                nextKey = if (result.data?.results?.isEmpty() == true) null else currentPage + params.loadSize
            )
            is APIError -> LoadResult.Error(apiErrorMapper.map(result))
            else -> LoadResult.Error(Error.Unmapped(""))
        }
    }
}