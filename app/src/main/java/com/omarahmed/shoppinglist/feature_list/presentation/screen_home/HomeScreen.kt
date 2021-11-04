package com.omarahmed.shoppinglist.feature_list.presentation.screen_home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.omarahmed.shoppinglist.feature_list.presentation.screen_home.components.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.feature_list.util.items
import kotlinx.coroutines.flow.collectLatest


@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val allItems = viewModel.items.collectAsLazyPagingItems()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
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
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                bottom = SuperLargeSpace,
                start = SmallSpace,
                end = SmallSpace,
                top = SmallSpace
            ),
        ) {
            if (allItems.loadState.refresh == LoadState.Loading) {
                item {
                    CircularProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(CenterHorizontally))
                }

            }
            items(allItems.itemCount) { index ->
                allItems[index]?.let {
                    ShoppingItem(shoppingItem = it)
                }
            }
            if (allItems.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally))
                }
            }

        }

    }
}


