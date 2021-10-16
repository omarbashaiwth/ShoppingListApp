package com.omarahmed.shoppinglist.data

data class ShoppingItem(
    val name: String,
    val imageUrl: String?,
    val isAddedToCart: Boolean = false,
    val id: String
)
