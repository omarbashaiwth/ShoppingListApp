package com.omarahmed.shoppinglist.features.feature_auth.presentation.util

import com.omarahmed.shoppinglist.core.util.Error

sealed class AuthError: Error() {
    object TooShortInputError: AuthError()
    object InvalidEmailError: AuthError()
    object InvalidPasswordError: AuthError()
}
