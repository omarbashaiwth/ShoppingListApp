package com.omarahmed.shoppinglist.core.data.model

data class ShoppingItem(
    val name: String,
    val imageUrl: String?,
    val isAddedToCart: Boolean = false,
    val id: String
)
