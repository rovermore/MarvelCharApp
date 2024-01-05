package com.example.marvelcharapp.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvelcharapp.presentation.base.ErrorUI
import com.example.marvelcharapp.presentation.widgets.ErrorView

@Composable
fun MainFragmentView(
    onCharacterClicked: (CharacterUIModel) -> Unit,
    viewModel: MainViewModel = viewModel()
    ) {
    MainFragmentMainView(onCharacterClicked, viewModel.characterState) { offset ->
        viewModel.getCharacterList(offset)
    }
}

@Composable
fun MainFragmentMainView(
    onCharacterClicked: (CharacterUIModel) -> Unit,
    state: State<MainViewModel.CharactersState>,
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
        when (val s = state.value) {
            MainViewModel.CharactersState.Loading ->
                CircularProgressIndicator(
                    modifier = Modifier
                        .width(32.dp)
                        .align(Alignment.Center)
                )

            is MainViewModel.CharactersState.Success ->
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    state = listState
                ) {
                    items(s.characterList) { character ->
                        CharacterItem(character, onCharacterClicked)
                    }
                }

            is MainViewModel.CharactersState.Error ->
                ErrorView(
                    modifier = Modifier.align(Alignment.Center)
                ) { fetchMoreCharacters(20) }
        }
    }
}


@Preview(widthDp = 340, showBackground = true , backgroundColor = 0xFFFFFF)
@Composable
fun MainFragmentPreview() {
    MainFragmentMainView(
        onCharacterClicked = {},
        state = remember { mutableStateOf<MainViewModel.CharactersState>(MainViewModel.CharactersState.Error(ErrorUI.GenericError(""))) },
        fetchMoreCharacters = {}
    )
}
