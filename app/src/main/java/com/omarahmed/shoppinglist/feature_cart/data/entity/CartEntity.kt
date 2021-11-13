package com.omarahmed.shoppinglist.feature_cart.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omarahmed.shoppinglist.core.util.Constants

@Entity(tableName = Constants.ITEMS_TABLE)
data class CartEntity(

    @ColumnInfo(name = "item_name")
    val itemName: String,
    @ColumnInfo(name = "icon_url")
    val itemIconUrl: String,
    @ColumnInfo(name = "already_bought")
    val isBought: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val itemId: Long = 0L,
)
