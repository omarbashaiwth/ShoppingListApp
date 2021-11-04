package com.omarahmed.shoppinglist.feature_list.presentation.screen_add_item

sealed class AddItemEvent{
    data class EnteredName(val name: String): AddItemEvent()
    object SaveItem: AddItemEvent()
}
