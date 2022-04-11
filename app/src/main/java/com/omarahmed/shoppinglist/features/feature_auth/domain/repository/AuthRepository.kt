package com.omarahmed.shoppinglist.features.feature_auth.domain.repository

import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_auth.data.remote.response.LoginResponse

interface AuthRepository {

    suspend fun createUser(
        name: String,
        email:String,
        password: String
    ): Resource<Unit>

    suspend fun loginUser(
        email: String,
        password: String
    ): Resource<LoginResponse>

    suspend fun authenticate(token: String?): Resource<Unit>

    suspend fun logout()
}