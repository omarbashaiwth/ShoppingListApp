package com.omarahmed.shoppinglist.feature_cart.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omarahmed.shoppinglist.feature_cart.data.entity.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM shopping_items")
    fun getAllItems(): Flow<List<CartEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: CartEntity)
}