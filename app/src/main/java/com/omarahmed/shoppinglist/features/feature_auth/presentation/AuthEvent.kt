package com.omarahmed.shoppinglist.features.feature_auth.presentation

sealed class AuthEvent{
    data class OnNameChanged(val name: String): AuthEvent()
    data class OnEmailChanged(val email: String): AuthEvent()
    data class OnPasswordChanged(val password: String): AuthEvent()
    data class OnPasswordToggleVisibility(val isPasswordVisible: Boolean): AuthEvent()
    object OnCreateUser: AuthEvent()
    object OnLoginUser: AuthEvent()
}
