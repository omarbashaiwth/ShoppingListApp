package com.omarahmed.shoppinglist.feature_list.presentation.screen_add_item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(): ViewModel() {

    private val _itemNameState = mutableStateOf(TextFieldState())
    val itemNameState: State<TextFieldState> = _itemNameState

    fun onEvent(event: AddItemEvent){
        when(event){
            is AddItemEvent.EnteredName -> {
                _itemNameState.value = _itemNameState.value.copy(
                    text = event.name
                )
            }
            is AddItemEvent.SaveItem -> {

            }
        }
    }

}