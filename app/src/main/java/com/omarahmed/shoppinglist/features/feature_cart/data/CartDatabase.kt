package com.omarahmed.shoppinglist.features.feature_cart.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity

@Database(entities = [CartEntity::class], version = 1, exportSchema = false)
abstract class CartDatabase: RoomDatabase() {

    abstract fun itemDao(): CartDao
}