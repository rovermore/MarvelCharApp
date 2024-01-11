package com.example.marvelcharapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val characterUseCase: CharacterUseCase,
    private val characterUIModelMapper: CharacterUIModelMapper,
    private val errorUIMapper: ErrorUIMapper
) : ViewModel() {

    private val _character: MutableLiveData<CharacterUIModel> = MutableLiveData()
    val character: LiveData<CharacterUIModel> get() = _character

    private val _error: MutableLiveData<ErrorUI> = MutableLiveData()
    val error: LiveData<ErrorUI> get() = _error

    private val _loading: MutableLiveData<Boolean> = MutableLiveData(true)
    val loading: LiveData<Boolean> get() = _loading



    fun getCharacter(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            characterUseCase.getCharacter(id)
                .map {
                    _character.postValue(characterUIModelMapper.map(it))
                    _loading.postValue(false)
                }
                .mapFailure {
                    _error.postValue(errorUIMapper.map(it))
                    _loading.postValue(false)
                }
        }
    }

}