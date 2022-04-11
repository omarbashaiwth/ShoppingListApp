package com.omarahmed.shoppinglist.features.feature_list.presentation.screen_home

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem

data class ShoppingListState(
    val shoppingItem: List<ShoppingItem> = emptyList(),
    val isLoading: Boolean = false,
    val page: Int = 0
)
