package com.omarahmed.shoppinglist.feature_list.presentation.screen_add_item

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.feature_list.domain.repository.ShoppingListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val repo: ShoppingListRepo,
): ViewModel() {

    private val _itemNameState = mutableStateOf(TextFieldState())
    val itemNameState: State<TextFieldState> = _itemNameState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _imageUriState = mutableStateOf<Uri?>(null)
    val imageUriState: State<Uri?> = _imageUriState


    fun onEvent(event: AddItemEvent){
        when(event){
            is AddItemEvent.EnteredName -> {
                _itemNameState.value = _itemNameState.value.copy(
                    text = event.name
                )
            }
            is AddItemEvent.PickImage -> {
                _imageUriState.value = event.uri
            }
            is AddItemEvent.SaveItem -> {
                viewModelScope.launch {
                    val result = imageUriState.value?.let {
                        repo.addNewItem(
                            itemName = itemNameState.value.text ?: "",
                            imageUri = it
                        )
                    }
                    when(result){
                        is Resource.Success -> {
                            _eventFlow.emit(UiEvent.Navigate())
                        }
                        is Resource.Error -> {
                            _eventFlow.emit(
                                UiEvent.ShowSnackbar(result.message ?: "Unknown error occurred")
                            )
                        }
                    }
                }

            }
        }
    }

}