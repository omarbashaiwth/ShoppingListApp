package com.omarahmed.shoppinglist.core.util

import androidx.annotation.StringRes


sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
