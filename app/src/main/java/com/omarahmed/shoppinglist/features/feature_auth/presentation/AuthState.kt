package com.omarahmed.shoppinglist.features.feature_auth.presentation

import com.omarahmed.shoppinglist.core.util.Error

data class AuthState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val error: Error? = null,
    val loading: Boolean = false
)
