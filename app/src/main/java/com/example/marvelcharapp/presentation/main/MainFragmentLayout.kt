package com.example.marvelcharapp.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvelcharapp.presentation.base.ErrorUI
import com.example.marvelcharapp.presentation.main.model.CharacterUIModel
import com.example.marvelcharapp.presentation.widgets.ErrorView
import com.example.marvelcharapp.presentation.widgets.Loader

@Composable
fun MainFragmentView(
    onCharacterClicked: (CharacterUIModel) -> Unit,
    viewModel: MainViewModel = viewModel()
    ) {

    val state by viewModel.characterState.collectAsStateWithLifecycle()

    Content(onCharacterClicked, state) { offset ->
        viewModel.getCharacterList(offset)
    }
}

@Composable
private fun Content(
    onCharacterClicked: (CharacterUIModel) -> Unit,
    state: CharactersState,
    fetchMoreCharacters: (Int) -> Unit
) {
    val listState = rememberLazyListState()
    var offset by rememberSaveable { mutableIntStateOf(20) }
    val isEndOfScroll by remember {
        derivedStateOf {
            !listState.canScrollForward
        }
    }

    LaunchedEffect(isEndOfScroll){
        if(isEndOfScroll) {
            offset += 20
            fetchMoreCharacters(offset)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val s = state) {
            CharactersState.Initial -> fetchMoreCharacters(20)
            CharactersState.Loading -> Loader()
            
            is CharactersState.Success ->
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    state = listState
                ) {
                    items(s.characterList) { character ->
                        CharacterItem(character, onCharacterClicked)
                    }
                }

            is CharactersState.Error ->
                ErrorView(
                    modifier = Modifier.align(Alignment.Center)
                ) { fetchMoreCharacters(20) }
        }
    }
}


@Preview(widthDp = 340, showBackground = true , backgroundColor = 0xFFFFFF)
@Composable
private fun MainFragmentPreview() {
    Content(
        onCharacterClicked = {},
        state = CharactersState.Error(ErrorUI.GenericError("")),
        fetchMoreCharacters = {}
    )
}
