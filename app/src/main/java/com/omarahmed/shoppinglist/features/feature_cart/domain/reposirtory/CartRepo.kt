package com.omarahmed.shoppinglist.features.feature_cart.domain.reposirtory

import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    suspend fun insertItem(item: CartEntity)

    val getAllItems: Flow<List<CartEntity>>

    suspend fun deleteItem(itemId: String)

    suspend fun updateItem(item: CartEntity)

}