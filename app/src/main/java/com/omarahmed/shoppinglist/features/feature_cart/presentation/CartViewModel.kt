package com.omarahmed.shoppinglist.features.feature_cart.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.features.feature_cart.domain.reposirtory.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: CartRepo
) : ViewModel() {

    private val _allItems = MutableLiveData<List<CartEntity>>(emptyList())
    val allItems = _allItems

    init {
        viewModelScope.launch {
            repo.getAllItems.collect {
                _allItems.postValue(it)
            }
        }
    }

    fun insertItem(item: CartEntity) = viewModelScope.launch {
        repo.insertItem(item)
    }

    fun deleteItem(itemId: String) = viewModelScope.launch {
        repo.deleteItem(itemId)
    }

    fun onBoughtStateChange(cartEntity: CartEntity) {
        val currentBoughtState = cartEntity.isBought
        val updatedItemCart = cartEntity.copy(isBought = !currentBoughtState)
        viewModelScope.launch {
            repo.updateItem(updatedItemCart)
        }
    }
}
