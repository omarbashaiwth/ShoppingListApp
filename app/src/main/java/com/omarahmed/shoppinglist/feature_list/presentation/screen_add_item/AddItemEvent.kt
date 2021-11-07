package com.omarahmed.shoppinglist.feature_list.presentation.screen_add_item

import android.net.Uri

sealed class AddItemEvent{
    data class EnteredName(val name: String): AddItemEvent()
    data class PickImage(val uri: Uri?): AddItemEvent()
    object SaveItem: AddItemEvent()
}
