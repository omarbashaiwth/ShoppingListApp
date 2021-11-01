package com.omarahmed.shoppinglist.feature_search.presentation

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem

data class SearchState(
    val searchResult: List<ShoppingItem> = emptyList(),
    val isLoading: Boolean = false
)
