package com.omarahmed.shoppinglist.presentation.home

import com.omarahmed.shoppinglist.data.ShoppingItem

data class ShoppingListState(
    val shoppingList: List<ShoppingItem> = listOf(),
    val isLoading: Boolean = false
)
