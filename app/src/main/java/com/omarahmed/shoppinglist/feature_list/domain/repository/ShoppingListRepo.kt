package com.omarahmed.shoppinglist.feature_list.domain.repository

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.util.Resource

interface ShoppingListRepo {

    suspend fun getAllItems(): Resource<List<ShoppingItem>>
}