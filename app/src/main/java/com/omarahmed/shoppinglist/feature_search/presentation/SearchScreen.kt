package com.omarahmed.shoppinglist.feature_search.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.omarahmed.shoppinglist.feature_list.presentation.components.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SmallSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SuperLargeSpace


@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel()
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

    }
}