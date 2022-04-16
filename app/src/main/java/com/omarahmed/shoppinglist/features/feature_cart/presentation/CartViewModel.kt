package com.omarahmed.shoppinglist.features.feature_cart.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.features.feature_cart.domain.reposirtory.CartRepo
import com.omarahmed.shoppinglist.features.feature_list.domain.repository.ShoppingListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: CartRepo,
    private val shoppingListRepo: ShoppingListRepo
) : ViewModel() {


    private val cartItemsState = mutableStateOf(CartState())
    val cartItems: State<CartState> = cartItemsState

    private var getNotesJob: Job? = null

    init {
        onEvent(CartEvent.OnGetAllItems)
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.OnInsertItem -> viewModelScope.launch {
                repo.insertItem(event.item)
            }
            is CartEvent.OnDeleteItem -> viewModelScope.launch {
                repo.deleteItem(event.itemId)
            }
            is CartEvent.OnBoughtStateChanged -> {
                val currentBoughtState = event.cartEntity.isBought
                val updatedItemCart = event.cartEntity.copy(isBought = !currentBoughtState)
                viewModelScope.launch {
                    repo.updateItem(updatedItemCart)
                }
            }
            is CartEvent.OnDeleteAllItems -> viewModelScope.launch {
                repo.deleteAllItems()
                shoppingListRepo.updateAllItems(event.ids)
            }
            CartEvent.OnGetAllItems -> {
                getNotesJob?.cancel()
                getNotesJob = repo.getAllItems.onEach {
                    cartItemsState.value = cartItemsState.value.copy(
                        cartItems = it
                    )
                }.launchIn(viewModelScope)
            }
            CartEvent.OnDeleteAllClicked -> {
                cartItemsState.value = cartItemsState.value.copy(
                    showDialog = true
                )

            }
            is CartEvent.OnDeleteAllConfirmed -> viewModelScope.launch{
                cartItemsState.value = cartItemsState.value.copy(
                    showDialog = false
                )
                onEvent(CartEvent.OnDeleteAllItems(event.ids))
            }
            CartEvent.OnDeleteAllDismissed -> {
                cartItemsState.value = cartItemsState.value.copy(
                    showDialog = false
                )
            }
        }
    }
}
