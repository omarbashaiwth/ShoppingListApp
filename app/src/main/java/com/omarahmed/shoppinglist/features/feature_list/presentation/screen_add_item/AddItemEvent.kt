package com.omarahmed.shoppinglist.features.feature_list.presentation.screen_add_item

import android.net.Uri
import com.omarahmed.shoppinglist.features.feature_list.data.remote.request.AddItemRequest

sealed class AddItemEvent{
    data class EnteredName(val name: String): AddItemEvent()
    data class PickIcon(val url: String): AddItemEvent()
    object SaveItem: AddItemEvent()
}
