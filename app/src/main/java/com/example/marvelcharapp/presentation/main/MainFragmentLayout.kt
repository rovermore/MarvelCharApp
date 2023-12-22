package com.example.marvelcharapp.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvelcharapp.presentation.base.ErrorUI
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
    val characterList by viewModel.characterList.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val error by viewModel.error.collectAsStateWithLifecycle()

    var offset by rememberSaveable { mutableIntStateOf(30) }

    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (loading)
            CircularProgressIndicator(
                modifier = Modifier
                    .width(32.dp)
                    .align(Alignment.Center)
            )
        if (characterList.isNotEmpty())
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                state = listState
            ) {
                items(characterList) { character ->
                    CharacterItem(character, onCharacterClicked)
                    if (!listState.canScrollForward) {
                        offset += 30
                        viewModel.getCharacterList(offset)
                    }
                }
            }
        if (error !is ErrorUI.None)
            ErrorView(
                modifier = Modifier.align(Alignment.Center)
            ) { viewModel.getCharacterList(30) }
    }
}

/*
@Preview(widthDp = 340, showBackground = true , backgroundColor = 0xff0100)
@Composable
fun MainFragmentPreview() {
    MainFragmentView {}
}*/
