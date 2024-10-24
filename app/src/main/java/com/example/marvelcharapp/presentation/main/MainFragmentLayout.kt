package com.example.marvelcharapp.presentation.main

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.marvelcharapp.presentation.base.ErrorUI
import com.example.marvelcharapp.presentation.main.model.CharacterUIModel
import com.example.marvelcharapp.presentation.widgets.ErrorView
import com.example.marvelcharapp.presentation.widgets.ScreenContainer

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
    fetchCharacters: () -> Unit
) {
    ScreenContainer(
        onRefresh = fetchCharacters,
        isLoading = state is CharactersState.Loading,
        content = {
            val listState = rememberLazyListState()
            when (state) {
                is CharactersState.Initial -> fetchCharacters()
                is CharactersState.Success -> {
                    val characterPagingItems = state.list.collectAsLazyPagingItems()
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight(),
                        state = listState
                    ) {
                        characterPagingItems.apply {
                            if (characterPagingItems.loadState.refresh is LoadState.NotLoading) {
                                items(
                                    count = characterPagingItems.itemCount,
                                ) { index ->
                                    characterPagingItems[index]?.let {
                                        CharacterItem(
                                            it,
                                            onCharacterClicked
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                is CharactersState.Error -> ErrorView { fetchCharacters() }
                else -> {}
            }
        }
    )
}


@Preview(widthDp = 340, showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
private fun MainFragmentPreview() {
    Content(
        onCharacterClicked = {},
        state = CharactersState.Error(ErrorUI.GenericError("")),
        fetchCharacters = {}
    )
}
