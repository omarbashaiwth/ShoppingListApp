package com.omarahmed.shoppinglist.feature_auth.domain.use_case

import android.util.Log
import com.omarahmed.shoppinglist.feature_auth.domain.models.AuthResult
import com.omarahmed.shoppinglist.feature_auth.domain.repository.AuthRepository
import com.omarahmed.shoppinglist.feature_auth.presentation.util.ValidationUtil
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String
    ): AuthResult<Unit> {
        val emailError = ValidationUtil.validateEmail(email)
        val passwordError = ValidationUtil.validatePassword(password)

        if (emailError != null || passwordError != null){
            Log.d("createUser", "Successfully create user")
            return AuthResult(
                emailError = emailError,
                passwordError = passwordError
            )
        }
        val result = authRepository.createUser(name, email, password)

        return AuthResult(result = result)

    }
}