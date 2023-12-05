package com.example.marvelcharapp.presentation.main

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvelcharapp.presentation.widgets.ErrorView

@Composable
fun MainFragmentView(
    onCharacterClicked: (CharacterUIModel) -> Unit

) {
    MainFragmentMainView(onCharacterClicked)
}

@Composable
fun MainFragmentMainView(
    onCharacterClicked: (CharacterUIModel) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val list = mutableListOf<CharacterUIModel>()
    val receivedCharacters by viewModel.characterList.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()

    list.addAll(receivedCharacters)

    if (loading) {
        CircularProgressIndicator(modifier = Modifier.width(64.dp))
    } else {
        if (receivedCharacters.isNotEmpty())
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            itemsIndexed(list) { index, character ->
                CharacterItem(character, onCharacterClicked)
                if (index == list.size - 1)
                    viewModel.getCharacterList(20)
            }
            //TODO:Pendiente arreglar la posición de  la lista al cargar mas items
        }
        else
            ErrorView { viewModel.getCharacterList(20) }

    }

}

/*
@Preview(widthDp = 340, showBackground = true , backgroundColor = 0xff0100)
@Composable
fun MainFragmentPreview() {
    MainFragmentView {}
}*/
