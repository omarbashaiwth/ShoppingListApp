package com.omarahmed.shoppinglist.features.feature_list.data.remote

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.features.feature_list.data.remote.request.UpdateItemRequest
import com.omarahmed.shoppinglist.core.data.remote.response.BasicApiResponse
import com.omarahmed.shoppinglist.features.feature_list.data.remote.request.AddItemRequest
import okhttp3.MultipartBody
import retrofit2.http.*

interface ShoppingListApi {
    @GET("/api/items/get")
    suspend fun getAllItems(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<ShoppingItem>

    @GET("/api/items/search")
    suspend fun searchForItem(
        @Query("query") searchQuery: String
    ): List<ShoppingItem>


    @POST("/api/items/new_item")
    suspend fun addNewItem(
        @Header("Authorization") token: String,
        @Body request: AddItemRequest
    ): BasicApiResponse<Unit>


    @PUT("/api/item/update")
    suspend fun updateItem(
        @Header("Authorization") token: String,
        @Query("itemId") itemId: String,
        @Body request: UpdateItemRequest
    ): BasicApiResponse<ShoppingItem>
}