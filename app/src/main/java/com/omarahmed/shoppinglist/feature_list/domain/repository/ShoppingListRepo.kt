package com.omarahmed.shoppinglist.feature_list.domain.repository

import androidx.paging.PagingData
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.util.Resource
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepo {

    val allItems: Flow<PagingData<ShoppingItem>>
}