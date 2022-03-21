package com.omarahmed.shoppinglist.feature_auth.data.remote

import com.omarahmed.shoppinglist.core.data.remote.response.BasicApiResponse
import com.omarahmed.shoppinglist.feature_auth.data.remote.request.CreateUserRequest
import com.omarahmed.shoppinglist.feature_auth.data.remote.request.LoginUserRequest
import com.omarahmed.shoppinglist.feature_auth.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("/api/user/create")
    suspend fun createUser(
        @Body request: CreateUserRequest
    ): BasicApiResponse<Unit>

    @POST("/api/user/login")
    suspend fun loginUser(
        @Body request: LoginUserRequest
    ): BasicApiResponse<LoginResponse>

    @GET("/api/user/authenticate")
    suspend fun authenticate(
        @Header("Authorization") token: String
    )

}