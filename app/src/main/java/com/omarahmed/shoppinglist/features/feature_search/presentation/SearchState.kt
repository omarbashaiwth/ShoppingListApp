package com.omarahmed.shoppinglist.features.feature_search.presentation

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.util.Error

data class SearchState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: Error? = null,
    val searchResult: List<ShoppingItem> = emptyList(),
)
