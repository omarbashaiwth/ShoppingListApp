package com.omarahmed.shoppinglist.features.feature_auth.presentation.util

import android.util.Patterns

object ValidationUtil {

    fun validateEmail(email: String): AuthError? {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return AuthError.InvalidEmailError
        }

        return null
    }

    fun validatePassword(password: String): AuthError? {
        val upperCaseInPassword = password.any { it.isUpperCase() }
        val lowerCaseInPassword = password.any { it.isLowerCase() }
        val numberInPassword = password.any { it.isDigit() }
        val specialCharInPassword = password.any { !it.isLetterOrDigit() }
        if (!upperCaseInPassword || !lowerCaseInPassword ||!numberInPassword || !specialCharInPassword){
            return AuthError.InvalidPasswordError
        }

        if (password.trim().length < 8) {
            return AuthError.TooShortInputError
        }
        return null
    }
}