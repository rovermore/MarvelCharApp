package com.example.marvelcharapp.presentation.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelcharapp.domain.base.Error
import com.example.marvelcharapp.domain.base.peek
import com.example.marvelcharapp.domain.base.peekFailure
import com.example.marvelcharapp.domain.character.model.CharacterDTO
import com.example.marvelcharapp.domain.character.usecase.CharacterUseCase

class CharacterPagingSource(
    private val characterUseCase: CharacterUseCase
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
        characterUseCase.getCharacterList(currentPage).peek {
            return LoadResult.Page(
                data = it,
                prevKey = if (currentPage > STARTING_PAGE_INDEX) currentPage.minus(PAGE_SIZE) else null,
                nextKey = if (it.isEmpty()) null else currentPage + params.loadSize
            )
        }.peekFailure {
            return LoadResult.Error(it)
        }
        return LoadResult.Error(Error.OperationCompletedWithError(""))
    }
}