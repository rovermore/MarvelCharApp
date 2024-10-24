package com.example.marvelcharapp.presentation.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import com.example.marvelcharapp.presentation.base.Action
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(4.dp),
    background: Color = Color.Transparent,
    onRefresh: Action? = null,
    topBar: @Composable Action = {},
    bottomBar: @Composable Action = {},
    isLoading: Boolean = false,
    content: @Composable (PaddingValues) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(color = background) {
            Scaffold(
                modifier = modifier,
                scaffoldState = rememberScaffoldState(),
                topBar = topBar,
                bottomBar = bottomBar,
                backgroundColor = background,
                content = { contentPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = padding.calculateTopPadding(),
                                start = padding.calculateStartPadding(LocalLayoutDirection.current),
                                end = padding.calculateStartPadding(LocalLayoutDirection.current),
                                bottom = padding.calculateBottomPadding()
                            ),
                        content = {
                            onRefresh?.let {
                                SwipeRefresh(
                                    state = rememberSwipeRefreshState(isRefreshing = false),
                                    onRefresh = { onRefresh() },
                                    indicator = { state, trigger ->
                                        SwipeRefreshIndicator(
                                            state = state,
                                            refreshTriggerDistance = trigger,
                                            scale = true,
                                            arrowEnabled = false,
                                            contentColor = Color.Blue,
                                            largeIndication = true
                                        )
                                    },
                                    content = { content(contentPadding) }
                                )
                            } ?: kotlin.run {
                                content(contentPadding)
                            }
                        }
                    )
                }
            )
        }
        if (isLoading) {
            Loader()
        }
    }
}