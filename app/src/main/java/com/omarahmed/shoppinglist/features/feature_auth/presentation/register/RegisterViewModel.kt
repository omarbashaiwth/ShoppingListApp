package com.omarahmed.shoppinglist.features.feature_auth.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.omarahmed.shoppinglist.core.domain.states.PasswordFieldState
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.destinations.LoginScreenDestination
import com.omarahmed.shoppinglist.features.feature_auth.domain.use_case.RegisterUseCase
import com.omarahmed.shoppinglist.features.feature_auth.presentation.AuthEvent
import com.omarahmed.shoppinglist.features.feature_auth.presentation.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerResult: RegisterUseCase
) : ViewModel() {

    private val authRegisterState = mutableStateOf(AuthState())
    val authRegister: State<AuthState> = authRegisterState

    private val eventFlow = MutableSharedFlow<UiEvent>()
    val events = eventFlow.asSharedFlow()

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnNameChanged -> {
                authRegisterState.value = authRegisterState.value.copy(
                    name = event.name
                )
            }
            is AuthEvent.OnEmailChanged -> {
                authRegisterState.value = authRegisterState.value.copy(
                    email = event.email
                )
            }
            is AuthEvent.OnPasswordChanged -> {
                authRegisterState.value = authRegisterState.value.copy(
                    password = event.password
                )
            }
            is AuthEvent.OnPasswordToggleVisibility -> {
                authRegisterState.value = authRegisterState.value.copy(
                    isPasswordVisible = !event.isPasswordVisible
                )
            }
            AuthEvent.OnCreateUser -> {
                createUser()
            }
            else -> Unit
        }
    }

    private fun createUser() = viewModelScope.launch {
        authRegisterState.value = authRegisterState.value.copy(
            error = null,
            loading = true
        )

        val registerResult = registerResult(
            name = authRegisterState.value.name,
            email = authRegisterState.value.email,
            password = authRegisterState.value.password
        )

        if (registerResult.emailError != null) {
            authRegisterState.value = authRegisterState.value.copy(
                error = registerResult.emailError
            )
        }
        if (registerResult.passwordError != null) {
            authRegisterState.value = authRegisterState.value.copy(
                error = registerResult.passwordError
            )
        }
        when (registerResult.result) {
            is Resource.Success -> {
                eventFlow.emit(UiEvent.ShowSnackbar(registerResult.result.message ?: ""))
                authRegisterState.value = AuthState()
                delay(4000L)
                eventFlow.emit(UiEvent.Navigate(LoginScreenDestination()))
            }
            is Resource.Error -> {
                eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        registerResult.result.message ?: "Error, try again later"
                    )
                )
                authRegisterState.value = authRegisterState.value.copy(
                    loading = false
                )
            }
            null -> {
                authRegisterState.value = authRegisterState.value.copy(
                    loading = false
                )
            }
        }
    }
}