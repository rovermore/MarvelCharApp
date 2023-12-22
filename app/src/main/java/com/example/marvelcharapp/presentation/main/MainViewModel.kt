package com.example.marvelcharapp.presentation.main

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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val characterUIModelMapper: CharacterUIModelMapper,
    private val errorUIMapper: ErrorUIMapper
) : ViewModel() {

    private val _characterList = MutableStateFlow<MutableList<CharacterUIModel>>(mutableListOf())
    val characterList: StateFlow<MutableList<CharacterUIModel>> get() = _characterList.asStateFlow()

    private val _error = MutableStateFlow<ErrorUI>(ErrorUI.None)
    val error: StateFlow<ErrorUI> get() = _error.asStateFlow()

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> get() = _loading.asStateFlow()


    init {
        getCharacterList(30)
    }

    fun getCharacterList(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            characterUseCase.getCharacterList(offset)
                .map {
                   _characterList.value.addAll(characterUIModelMapper.mapList(it))
                }
                .mapFailure {
                    _error.value = errorUIMapper.map(it)
                }.then {
                    _loading.value = false
                }
        }
    }

}