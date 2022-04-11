package com.omarahmed.shoppinglist.features.feature_icon_search.data.remote

import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response.IconResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IconApi {

    @GET("icons/search")
    suspend fun searchIcon(
        @Query("query") query: String,
        @Query("count") count: Int = 50,
        @Query("premium") premium: String = "false",
    ): IconResponse
}