package com.omarahmed.shoppinglist.features.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.domain.states.PasswordFieldState
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.destinations.HomeScreenDestination
import com.omarahmed.shoppinglist.features.feature_auth.domain.use_case.LoginUseCase
import com.omarahmed.shoppinglist.features.feature_auth.presentation.AuthEvent
import com.omarahmed.shoppinglist.features.feature_auth.presentation.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginResult: LoginUseCase,
) : ViewModel() {

    private val authLoginState = mutableStateOf(AuthState())
    val authLogin: State<AuthState> = authLoginState

    private val eventFlow = MutableSharedFlow<UiEvent>()
    val events = eventFlow.asSharedFlow()


    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnEmailChanged -> {
                authLoginState.value = authLoginState.value.copy(
                    email = event.email
                )
            }
            is AuthEvent.OnPasswordChanged -> {
                authLoginState.value = authLoginState.value.copy(
                    password = event.password
                )
            }
            is AuthEvent.OnPasswordToggleVisibility -> {
                authLoginState.value = authLoginState.value.copy(
                    isPasswordVisible = !event.isPasswordVisible
                )
            }
            AuthEvent.OnLoginUser -> {
                loginUser()
            }
            else -> Unit
        }

    }


    private fun loginUser() = viewModelScope.launch {
        authLoginState.value = authLoginState.value.copy(
            error = null,
            loading = true
        )
        val loginResult = loginResult(
            email = authLoginState.value.email,
            password = authLoginState.value.password
        )
        if (loginResult.emailError != null) {
            authLoginState.value = authLoginState.value.copy(
                error = loginResult.emailError
            )
        }
        when (loginResult.result) {
            is Resource.Success -> {
                authLoginState.value = AuthState()
                eventFlow.emit(UiEvent.Navigate(HomeScreenDestination))
            }
            is Resource.Error -> {
                authLoginState.value = authLoginState.value.copy(
                    loading = false
                )
                eventFlow.emit(
                    UiEvent.ShowSnackbar(
                        loginResult.result.message ?: "Error, try again later"
                    )
                )
            }
            null -> {
                authLoginState.value = authLoginState.value.copy(
                    loading = false
                )
            }
        }

    }

}