package com.example.marvelcharapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelcharapp.domain.base.map
import com.example.marvelcharapp.domain.base.mapFailure
import com.example.marvelcharapp.domain.character.usecase.CharacterUseCase
import com.example.marvelcharapp.presentation.base.ErrorUI
import com.example.marvelcharapp.presentation.base.ErrorUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val characterUIModelMapper: CharacterUIModelMapper,
    private val errorUIMapper: ErrorUIMapper
) : ViewModel() {

    private val _characterList: MutableLiveData<List<CharacterUIModel>> = MutableLiveData()
    val characterList: LiveData<List<CharacterUIModel>> get() = _characterList

    private val _error: MutableLiveData<ErrorUI> = MutableLiveData()
    val error: LiveData<ErrorUI> get() = _error

    fun getCharacterList(offset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            characterUseCase.getCharacterList(offset)
                .map {
                   _characterList.postValue(characterUIModelMapper.mapList(it))
                }
                .mapFailure {
                    _error.postValue(errorUIMapper.map(it))
                }
        }
    }

}