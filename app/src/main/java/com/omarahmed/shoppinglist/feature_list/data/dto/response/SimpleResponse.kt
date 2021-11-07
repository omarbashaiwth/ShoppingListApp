package com.omarahmed.shoppinglist.feature_list.data.dto.response

data class SimpleResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)