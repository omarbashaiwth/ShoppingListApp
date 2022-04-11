package com.omarahmed.shoppinglist.features.feature_auth.domain.models

import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_auth.presentation.util.AuthError

data class AuthResult<T>(
    val emailError: AuthError? = null,
    val passwordError: AuthError? = null,
    val result: Resource<T>? = null
)
