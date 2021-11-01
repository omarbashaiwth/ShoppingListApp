package com.omarahmed.shoppinglist.feature_list.presentation.screen_home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.omarahmed.shoppinglist.feature_list.presentation.screen_home.components.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import kotlinx.coroutines.flow.collectLatest


@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading){
            CircularProgressIndicator()
        }
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                bottom = SuperLargeSpace,
                start = SmallSpace,
                end = SmallSpace,
                top = SmallSpace
            ),
        ) {
            items(state.shoppingItem.size) {
                ShoppingItem(shoppingItem = state.shoppingItem[it])
            }
        }
    }

}


