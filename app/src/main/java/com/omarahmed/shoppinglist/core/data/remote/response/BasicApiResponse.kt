package com.omarahmed.shoppinglist.core.data.remote.response

import androidx.annotation.StringRes

data class BasicApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)