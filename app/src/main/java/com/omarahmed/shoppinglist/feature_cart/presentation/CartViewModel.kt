package com.omarahmed.shoppinglist.feature_cart.presentation

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.feature_cart.domain.reposirtory.CartRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: CartRepo
): ViewModel() {

    private val _allItems = MutableLiveData<List<CartEntity>>(emptyList())
    val allItems = _allItems

    fun insertItem(item: CartEntity){
        viewModelScope.launch {
            repo.insertItem(item)
        }
    }

    init {
        viewModelScope.launch {
            repo.getAllItems.collect {
                _allItems.postValue(it)
            }
        }
    }

}