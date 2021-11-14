package com.omarahmed.shoppinglist.feature_list.presentation.screen_home

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.feature_list.domain.repository.ShoppingListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: ShoppingListRepo
) : ViewModel() {

    val items = repo.allItems.cachedIn(viewModelScope)

    fun updateItem(shoppingItem: ShoppingItem) {
        val isAddedToCart = shoppingItem.isAddedToCart
        val updatedItem = shoppingItem.copy(isAddedToCart = !isAddedToCart)
        viewModelScope.launch {
             repo.updateItem(
                itemId = shoppingItem.id,
                isAddedToCart = updatedItem.isAddedToCart
            )

        }
    }
}