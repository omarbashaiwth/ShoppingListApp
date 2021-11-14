package com.omarahmed.shoppinglist.core.data.remote

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.feature_list.data.dto.request.AddItemRequest
import com.omarahmed.shoppinglist.feature_list.data.dto.request.UpdateItemRequest
import com.omarahmed.shoppinglist.feature_list.data.dto.response.SimpleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ShoppingListApi {
    @GET("/api/items/get")
    suspend fun getAllItems(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): List<ShoppingItem>

    @GET("/api/items/search")
    suspend fun searchForItem(
        @Query("query") searchQuery: String
    ): List<ShoppingItem>


    @Multipart
    @POST("/api/items/new_item")
    suspend fun addNewItem(
        @Part postData: MultipartBody.Part,
        @Part postImage: MultipartBody.Part
    ): SimpleResponse<Unit>


    @PUT("/api/item/update")
    suspend fun updateItem(
        @Query("itemId") itemId: String,
        @Body request: UpdateItemRequest
    ): SimpleResponse<ShoppingItem>
}