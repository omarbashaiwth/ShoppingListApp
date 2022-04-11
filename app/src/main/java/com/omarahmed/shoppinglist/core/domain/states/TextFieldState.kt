package com.omarahmed.shoppinglist.core.domain.states

import com.omarahmed.shoppinglist.core.util.Error

data class TextFieldState(
    val text: String = "",
    val error: Error? = null
)
