package com.omarahmed.shoppinglist.features.feature_cart.presentation

import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity

data class CartState(
    val cartItems: List<CartEntity> = emptyList(),
    val showDialog: Boolean = false
)
