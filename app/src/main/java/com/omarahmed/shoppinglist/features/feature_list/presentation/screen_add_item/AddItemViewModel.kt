package com.omarahmed.shoppinglist.features.feature_list.presentation.screen_add_item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
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

    private val itemNameState = mutableStateOf(TextFieldState())
    val itemName: State<TextFieldState> = itemNameState

//    private val _itemNameState = mutableStateOf(TextFieldState())
//    val itemNameState: State<TextFieldState> = _itemNameState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val iconUrlState = mutableStateOf("")
    val iconUrl: State<String> = iconUrlState


    fun onEvent(event: AddItemEvent) {
        when (event) {
            is AddItemEvent.EnteredName -> {
                itemNameState.value = itemNameState.value.copy(
                    text = event.name
                )
            }
            is AddItemEvent.PickIcon -> {
                iconUrlState.value = event.url
            }
            is AddItemEvent.SaveItem -> {
                viewModelScope.launch {
                    repo.addNewItem(
                        itemName = itemNameState.value.text,
                        itemIconUrl = iconUrlState.value
                    )
                }
            }
        }
    }
}