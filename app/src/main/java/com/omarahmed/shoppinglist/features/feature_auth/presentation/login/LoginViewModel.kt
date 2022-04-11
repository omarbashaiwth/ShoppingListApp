package com.omarahmed.shoppinglist.features.feature_auth.presentation.login

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarahmed.shoppinglist.core.data.DataStoreManager
import com.omarahmed.shoppinglist.core.domain.states.PasswordFieldState
import com.omarahmed.shoppinglist.core.domain.states.TextFieldState
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.destinations.HomeScreenDestination
import com.omarahmed.shoppinglist.features.feature_auth.domain.repository.AuthRepository
import com.omarahmed.shoppinglist.features.feature_auth.domain.use_case.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginResult: LoginUseCase,
): ViewModel() {

    private val emailState = mutableStateOf(TextFieldState())
    val email: State<TextFieldState> = emailState

    private val passwordState = mutableStateOf(PasswordFieldState())
    val password: State<PasswordFieldState> = passwordState

    private val loadingState = mutableStateOf(false)
    val loading: State<Boolean> = loadingState

    private val eventChannel = Channel<UiEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onEmailChange(email: String) {
        emailState.value = emailState.value.copy(text = email)
    }

    fun onPasswordChange(password: String){
        passwordState.value = passwordState.value.copy(text = password)
    }

    fun onPasswordToggleVisibility(isPasswordVisible: Boolean){
        passwordState.value = passwordState.value.copy(isPasswordVisible = !isPasswordVisible )
    }

    fun onLoginUser() = viewModelScope.launch {
        emailState.value = emailState.value.copy(error = null)
        passwordState.value = passwordState.value.copy(error = null)
        loadingState.value = true

        val loginResult = loginResult(
            email = emailState.value.text,
            password = passwordState.value.text
        )
        if (loginResult.emailError != null) {
            emailState.value = emailState.value.copy(error = loginResult.emailError)
        }
        when(loginResult.result) {
            is Resource.Success -> {
                emailState.value = TextFieldState()
                passwordState.value = PasswordFieldState()
                loadingState.value = false
                eventChannel.send(UiEvent.Navigate(HomeScreenDestination))
            }
            is Resource.Error -> {
                loadingState.value = false
                eventChannel.send(UiEvent.ShowSnackbar(loginResult.result.message ?: "Error, try again later"))
            }
            null -> {
                loadingState.value = false
            }
        }

    }

}