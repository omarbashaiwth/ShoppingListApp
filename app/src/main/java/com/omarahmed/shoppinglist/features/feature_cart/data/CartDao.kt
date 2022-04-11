package com.omarahmed.shoppinglist.features.feature_cart.data

import androidx.room.*
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM shopping_items")
    fun getAllItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartEntity)

    @Query("DELETE FROM shopping_items")
    suspend fun deleteAllItems()

    @Query("DELETE FROM shopping_items WHERE itemId ==:itemId")
    suspend fun deleteItem(itemId: String)

    @Update
    suspend fun updateItem(item: CartEntity)
}