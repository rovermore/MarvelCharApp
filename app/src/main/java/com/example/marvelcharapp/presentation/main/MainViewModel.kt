package com.example.marvelcharapp.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelcharapp.domain.base.map
import com.example.marvelcharapp.domain.base.mapFailure
import com.example.marvelcharapp.domain.base.then
import com.example.marvelcharapp.domain.character.usecase.CharacterUseCase
import com.example.marvelcharapp.presentation.base.ErrorUI
import com.example.marvelcharapp.presentation.base.ErrorUIMapper
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

    private val _characterList = mutableStateListOf<CharacterUIModel>()
    val characterList: List<CharacterUIModel> get() = _characterList

    private val _error = mutableStateOf<ErrorUI>(ErrorUI.None)
    val error: State<ErrorUI> get() = _error

    private val _loading = mutableStateOf<Boolean>(true)
    val loading: State<Boolean> get() = _loading

    private val _characterState = mutableStateOf<CharactersState>(CharactersState.Loading)
    val characterState: State<CharactersState> get() = _characterState


    init {
        getCharacterList(20)
    }

    fun getCharacterList(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _characterState.value = CharactersState.Loading }
            characterUseCase.getCharacterList(offset)
                .map {
                    _characterState.value = CharactersState.Success.apply { characterList.addAll(characterUIModelMapper.mapList(it)) }
                }
                .mapFailure {
                    _characterState.value = CharactersState.Error(errorUIMapper.map(it))
                }
        }
    }


    sealed class CharactersState {
        data object Success: CharactersState() { val characterList = mutableListOf<CharacterUIModel>() }
        data class Error(val error: ErrorUI): CharactersState()
        data object Loading: CharactersState()
    }

}