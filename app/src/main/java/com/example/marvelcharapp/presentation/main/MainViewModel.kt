package com.example.marvelcharapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.marvelcharapp.domain.character.usecase.CharacterUseCase
import com.example.marvelcharapp.presentation.base.ErrorUI
import com.example.marvelcharapp.presentation.base.ErrorUIMapper
import com.example.marvelcharapp.presentation.main.model.CharacterUIModel
import com.example.marvelcharapp.presentation.main.model.CharacterUIModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val characterUIModelMapper: CharacterUIModelMapper,
) : ViewModel() {

    private val _characterState = MutableStateFlow<CharactersState>(CharactersState.Initial)
    val characterState: StateFlow<CharactersState> get() = _characterState.asStateFlow()

    fun getCharacterList() {
        viewModelScope.launch {
            _characterState.value = CharactersState.Loading
            withContext(Dispatchers.IO) {
                Pager(
                    config = PagingConfig(
                        pageSize = CharacterPagingSource.PAGE_SIZE,
                        prefetchDistance = 25,
                        initialLoadSize = 30
                    ),
                    pagingSourceFactory = {
                        CharacterPagingSource(
                            characterUseCase
                        )
                    }
                ).flow
                    .cachedIn(viewModelScope)
                    .onEmpty {
                        _characterState.value = CharactersState.Error(ErrorUI.GenericError(""))
                    }
                    .collect {
                        _characterState.value = CharactersState.Success(MutableStateFlow(it.map { characterDTO ->  characterUIModelMapper.map(characterDTO) }))
                    }

            }
        }
    }
}

sealed class CharactersState {
    data object Initial: CharactersState()
    data class Success(val list: MutableStateFlow<PagingData<CharacterUIModel>>): CharactersState()
    data class Error(val error: ErrorUI): CharactersState()
    data object Loading: CharactersState()
}