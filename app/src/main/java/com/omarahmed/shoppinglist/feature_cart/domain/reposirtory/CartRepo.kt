package com.omarahmed.shoppinglist.feature_cart.domain.reposirtory

import com.omarahmed.shoppinglist.feature_cart.data.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface CartRepo {

    suspend fun insertItem(item: CartEntity)

    val getAllItems: Flow<List<CartEntity>>

}