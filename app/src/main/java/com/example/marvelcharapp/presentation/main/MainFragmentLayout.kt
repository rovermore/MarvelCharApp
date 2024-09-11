package com.example.marvelcharapp.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
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

    Content(onCharacterClicked, state) {
        viewModel.getCharacterList()
    }
}

@Composable
private fun Content(
    onCharacterClicked: (CharacterUIModel) -> Unit,
    state: CharactersState,
    fetchMoreCharacters: () -> Unit
) {
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            CharactersState.Initial -> fetchMoreCharacters()
            CharactersState.Loading -> Loader()
            
            is CharactersState.Success -> {
                val characterPagingItems = state.list.collectAsLazyPagingItems()
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    state = listState
                ) {

                    characterPagingItems.apply {
                        when(characterPagingItems.loadState.refresh) {

                            is LoadState.NotLoading -> {
                                items(
                                    count = characterPagingItems.itemCount,
                                ) {index ->
                                    characterPagingItems[index]?.let { CharacterItem(it, onCharacterClicked) }
                                }
                            }

                            is LoadState.Loading -> item { Loader() }

                            else -> {}
                        }
                    }
                }
            }

            is CharactersState.Error ->
                ErrorView(
                    modifier = Modifier.align(Alignment.Center)
                ) { fetchMoreCharacters() }
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
