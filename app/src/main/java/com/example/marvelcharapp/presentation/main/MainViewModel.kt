package com.example.marvelcharapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelcharapp.domain.base.map
import com.example.marvelcharapp.domain.base.mapFailure
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val characterUIModelMapper: CharacterUIModelMapper,
    private val errorUIMapper: ErrorUIMapper
) : ViewModel() {

    private val _characterState = MutableStateFlow<CharactersState>(CharactersState.Loading)
    val characterState: StateFlow<CharactersState> get() = _characterState.asStateFlow()

    init {
        getCharacterList(20)
    }

    fun getCharacterList(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _characterState.value = CharactersState.Loading }
            characterUseCase.getCharacterList(offset)
                .map {
                    _characterState.emit(
                        CharactersState.Success.apply {
                            characterList.addAll(characterUIModelMapper.mapList(it))
                        }
                    )
                }
                .mapFailure {
                    _characterState.value = CharactersState.Error(errorUIMapper.map(it))
                }
        }
    }
}

sealed class CharactersState {
    data object Success: CharactersState() { val characterList = mutableListOf<CharacterUIModel>() }
    data class Error(val error: ErrorUI): CharactersState()
    data object Loading: CharactersState()
}