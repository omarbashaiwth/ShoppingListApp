package com.omarahmed.shoppinglist.features.feature_cart.presentation

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

    private val _allItems = mutableStateOf<List<CartEntity>>(emptyList())
    val allItems = _allItems

    private val showDialogState = mutableStateOf(false)
    val showDialog = showDialogState

    private var getNotesJob: Job? = null

    init {
        getAllItems()
    }

    private fun getAllItems() {
        getNotesJob?.cancel()
        getNotesJob = repo.getAllItems.onEach {
            _allItems.value = it
        }
            .launchIn(viewModelScope)
    }


    fun insertItem(item: CartEntity) = viewModelScope.launch {
        repo.insertItem(item)
    }

    fun deleteItem(itemId: String) = viewModelScope.launch {
        repo.deleteItem(itemId)
    }

    fun deleteAllItems(ids: List<String>) = viewModelScope.launch {
        repo.deleteAllItems()
        shoppingListRepo.updateAllItems(ids)
    }

    fun onBoughtStateChange(cartEntity: CartEntity) {
        val currentBoughtState = cartEntity.isBought
        val updatedItemCart = cartEntity.copy(isBought = !currentBoughtState)
        viewModelScope.launch {
            repo.updateItem(updatedItemCart)
        }
    }

    fun onDeleteAllClicked(){
        showDialogState.value = true
    }

    fun onDeleteAllConfirmed(ids: List<String>) = viewModelScope.launch{
        showDialogState.value = false
        deleteAllItems(ids)
    }

    fun onDeleteAllDismissed(){
        showDialogState.value = false
    }
}
