package com.omarahmed.shoppinglist.features.feature_list.presentation.screen_add_item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.features.feature_list.domain.repository.ShoppingListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repo: ShoppingListRepo,
) : ViewModel() {


    private val _addItemState = mutableStateOf(AddItemState())
    val addItemState: State<AddItemState> = _addItemState

    private val eventFlow = MutableSharedFlow<UiEvent>()
    val events = eventFlow.asSharedFlow()

    fun onEvent(event: AddItemEvent) {
        when (event) {
            is AddItemEvent.EnteredName -> {
                _addItemState.value = _addItemState.value.copy(
                    itemName = event.name
                )
            }
            is AddItemEvent.PickIcon -> {
                _addItemState.value = _addItemState.value.copy(
                    iconUrl = event.url
                )
            }
            is AddItemEvent.SaveItem -> {
                viewModelScope.launch {
                    repo.addNewItem(
                        itemName = _addItemState.value.itemName,
                        itemIconUrl = _addItemState.value.iconUrl
                    )
                }
            }
        }
    }
}