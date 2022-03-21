package com.omarahmed.shoppinglist.core.domain.states

import com.omarahmed.shoppinglist.core.util.Error

data class PasswordFieldState(
    val text: String = "",
    val error: Error? = null,
    val isPasswordVisible: Boolean = false
)
