package com.omarahmed.shoppinglist.feature_list.presentation.screen_home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.feature_list.domain.repository.ShoppingListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shoppingListRepo: ShoppingListRepo
):ViewModel() {

    private val _state = mutableStateOf(ShoppingListState())
    val state: State<ShoppingListState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getAllItems()
    }

    private fun getAllItems(){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when(val result = shoppingListRepo.getAllItems()){
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        shoppingItem = result.data ?: emptyList(),
                        isLoading = false
                    )
                }
                is Resource.Error -> {
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.message ?: "Unknown error occurred"))
                    _state.value = _state.value.copy(isLoading = false)
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
    }

}