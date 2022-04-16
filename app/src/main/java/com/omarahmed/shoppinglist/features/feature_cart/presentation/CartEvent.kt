package com.omarahmed.shoppinglist.features.feature_cart.presentation

import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity

sealed class CartEvent{
    data class OnInsertItem(val item: CartEntity): CartEvent()
    data class OnDeleteItem(val itemId: String): CartEvent ()
    data class OnDeleteAllItems(val ids: List<String>): CartEvent()
    data class OnBoughtStateChanged(val cartEntity: CartEntity): CartEvent()
    object OnGetAllItems: CartEvent()
    object OnDeleteAllClicked: CartEvent()
    data class OnDeleteAllConfirmed(val ids: List<String>): CartEvent()
    object OnDeleteAllDismissed: CartEvent()
}
