package com.omarahmed.shoppinglist.core.data.remote

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.util.Resource
import retrofit2.http.GET
import retrofit2.http.Query

interface ShoppingListApi {
    @GET("/api/items/get")
    suspend fun getAllItems(): List<ShoppingItem>

    @GET("/api/items/search")
    suspend fun searchForItem(
        @Query("query") searchQuery: String
    ): List<ShoppingItem>
}