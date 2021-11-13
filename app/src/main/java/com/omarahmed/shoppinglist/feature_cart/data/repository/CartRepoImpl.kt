package com.omarahmed.shoppinglist.feature_cart.data.repository

import com.omarahmed.shoppinglist.feature_cart.data.CartDao
import com.omarahmed.shoppinglist.feature_cart.data.CartDatabase
import com.omarahmed.shoppinglist.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.feature_cart.domain.reposirtory.CartRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepoImpl @Inject constructor(
    private val dao: CartDao
): CartRepo {

    override suspend fun insertItem(item: CartEntity) {
        dao.insertItem(item)
    }

    override val getAllItems: Flow<List<CartEntity>>
        get() = dao.getAllItems()

    override suspend fun deleteItem(item: CartEntity) {
        dao.deleteItem(item)
    }

    override suspend fun updateItem(item: CartEntity) {
        dao.updateItem(item)
    }
}