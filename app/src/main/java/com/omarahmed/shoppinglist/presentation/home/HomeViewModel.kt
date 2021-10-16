package com.omarahmed.shoppinglist.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.data.remote.ShoppingListApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: ShoppingListApi
):ViewModel() {

    private val _state = mutableStateOf(ShoppingListState())
    val state: State<ShoppingListState> = _state

    init {
        getAllItems()
    }

    fun getAllItems(){
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true)
                _state.value = _state.value.copy(
                    shoppingList = api.getAllItems(),
                    isLoading = false
                )

            }catch (e:Exception){
                _state.value = _state.value.copy(isLoading = false)
                Log.d("HOME_SCREEN",e.localizedMessage)
            }
        }
    }

}