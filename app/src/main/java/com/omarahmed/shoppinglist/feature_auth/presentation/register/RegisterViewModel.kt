package com.omarahmed.shoppinglist.feature_auth.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.omarahmed.shoppinglist.core.domain.states.PasswordFieldState
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.destinations.LoginScreenDestination
import com.omarahmed.shoppinglist.feature_auth.domain.use_case.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerResult: RegisterUseCase
) : ViewModel() {

    private val nameState = mutableStateOf(TextFieldState())
    val name: State<TextFieldState> = nameState

    private val emailState = mutableStateOf(TextFieldState())
    val email: State<TextFieldState> = emailState

    private val passwordState = mutableStateOf(PasswordFieldState())
    val password: State<PasswordFieldState> = passwordState

    private val loadingState = mutableStateOf(false)
    val loading: State<Boolean> = loadingState

    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onNameChange(name: String) {
        nameState.value = nameState.value.copy(text = name)
    }

    fun onEmailChange(email: String) {
        emailState.value = emailState.value.copy(text = email)
    }

    fun onPasswordChange(password: String) {
        passwordState.value = passwordState.value.copy(text = password)
    }

    fun onPasswordToggleVisibility(isPasswordVisible: Boolean) {
        passwordState.value = passwordState.value.copy(isPasswordVisible = !isPasswordVisible)
    }

    fun onCreateUser() = viewModelScope.launch {
        nameState.value = nameState.value.copy(error = null)
        emailState.value = emailState.value.copy(error = null)
        passwordState.value = passwordState.value.copy(error = null)
        loadingState.value = true

        val registerResult = registerResult(
            name = nameState.value.text,
            email = emailState.value.text,
            password = passwordState.value.text
        )

        if (registerResult.emailError != null) {
            emailState.value = emailState.value.copy(error = registerResult.emailError)
        }
        if (registerResult.passwordError != null) {
            passwordState.value = passwordState.value.copy(error = registerResult.passwordError)
        }
        when (registerResult.result) {
            is Resource.Success -> {
                eventChannel.send(UiEvent.ShowSnackbar(registerResult.result.message ?: ""))
                loadingState.value = false
                delay(4000L)
                eventChannel.send(UiEvent.Navigate(LoginScreenDestination()))
                nameState.value = TextFieldState()
                emailState.value = TextFieldState()
                passwordState.value = PasswordFieldState()

            }
            is Resource.Error -> {
                eventChannel.send(
                    UiEvent.ShowSnackbar(
                        registerResult.result.message ?: "Error, try again later"
                    )
                )
                loadingState.value = false

            }
            null -> {
                loadingState.value = false

            }
        }
    }
}