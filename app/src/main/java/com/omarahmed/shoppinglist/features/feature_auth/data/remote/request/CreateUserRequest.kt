package com.omarahmed.shoppinglist.features.feature_auth.data.remote.request

data class CreateUserRequest(
    val name: String,
    val email: String,
    val password: String
)
