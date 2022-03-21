package com.omarahmed.shoppinglist.feature_auth.domain.use_case

import com.omarahmed.shoppinglist.feature_auth.data.remote.response.LoginResponse
import com.omarahmed.shoppinglist.feature_auth.domain.models.AuthResult
import com.omarahmed.shoppinglist.feature_auth.domain.repository.AuthRepository
import com.omarahmed.shoppinglist.feature_auth.presentation.util.ValidationUtil
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): AuthResult<LoginResponse>{
        val emailError = ValidationUtil.validateEmail(email)
        val passwordError = ValidationUtil.validatePassword(password)

        if (emailError != null || passwordError != null) {
            return AuthResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }

        val result = authRepository.loginUser(email, password)
        return AuthResult(
            result = result
        )
    }
}