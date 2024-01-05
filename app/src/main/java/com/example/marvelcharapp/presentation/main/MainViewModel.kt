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


    init {
        getCharacterList(20)
    }

    fun getCharacterList(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) { _loading.value = true }
            characterUseCase.getCharacterList(offset)
                .map {
                    _characterList.addAll(characterUIModelMapper.mapList(it))
                }
                .mapFailure {
                    _error.value = errorUIMapper.map(it)
                }.then {
                    _loading.value = false
                }
        }
    }

}