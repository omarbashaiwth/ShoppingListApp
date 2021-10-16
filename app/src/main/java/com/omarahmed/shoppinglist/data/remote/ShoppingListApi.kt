package com.omarahmed.shoppinglist.data.remote

import com.omarahmed.shoppinglist.data.ShoppingItem
import retrofit2.http.GET

interface ShoppingListApi {
    @GET("/api/items/get")
    suspend fun getAllItems(): List<ShoppingItem>
}