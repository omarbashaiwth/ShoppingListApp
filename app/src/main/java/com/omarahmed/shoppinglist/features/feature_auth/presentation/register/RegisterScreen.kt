package com.omarahmed.shoppinglist.features.feature_auth.presentation.register

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.component.StandardTextField
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.features.destinations.LoginScreenDestination
import com.omarahmed.shoppinglist.features.destinations.RegisterScreenDestination
import com.omarahmed.shoppinglist.features.feature_auth.presentation.AuthEvent
import com.omarahmed.shoppinglist.features.feature_auth.presentation.util.AuthError
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import javax.crypto.AEADBadTagException

@Destination()
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    hideBottomNav: Boolean = true,
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current
    val state by viewModel.authRegister

    LaunchedEffect(key1 = scaffoldState){
        viewModel.events.collectLatest { event ->
            when(event){
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is UiEvent.Navigate -> {
                    navigator.navigate(event.destination)
                }
            }
        }
    }
    Scaffold(scaffoldState = scaffoldState) {
        if (state.loading){
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = White
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(LargeSpace),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.fillMaxWidth()){
                drawCircle(
                    color = GreenAccent,
                    radius = size.width/1.5f
                )
            }
            Image(
                painter = painterResource(id = R.drawable.shopping_cart),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 30.dp)
                    .size(150.dp)
            )
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SuperLargeSpace * 2))
            StandardTextField(
                value = state.name,
                hint = stringResource(id = R.string.name),
                onValueChange = {
                    if (it.length <= 20) {
                        viewModel.onEvent(AuthEvent.OnNameChanged(it))
                    }
                },
                leadingIcon = Icons.Filled.Person,
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            StandardTextField(
                value = state.email.lowercase(),
                hint = stringResource(id = R.string.email),
                onValueChange = {
                    viewModel.onEvent(AuthEvent.OnEmailChanged(it))
                },
                leadingIcon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email,
                error = when (state.error) {
                    is AuthError.InvalidEmailError -> stringResource(id = R.string.invalid_email_msg)
                    else -> ""
                },
                imeAction = ImeAction.Next,
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                )
            )
            Spacer(modifier = Modifier.height(MediumSpace))
            StandardTextField(
                value = state.password,
                hint = stringResource(id = R.string.password),
                onValueChange = {
                    viewModel.onEvent(AuthEvent.OnPasswordChanged(it))
                },
                leadingIcon = Icons.Filled.Lock,
                trailingIcon = if (state.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                isPasswordVisible = state.isPasswordVisible,
                onTrailingIconClick = {
                    viewModel.onEvent(AuthEvent.OnPasswordToggleVisibility(it))
                },
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                error = when (state.error) {
                    is AuthError.TooShortInputError -> stringResource(id = R.string.too_short_password_msg)
                    is AuthError.InvalidPasswordError -> stringResource(id = R.string.invalid_password_msg)
                    else -> ""
                },
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = {viewModel.onEvent(AuthEvent.OnCreateUser)}
                )
            )
            Spacer(modifier = Modifier.height(SuperLargeSpace))
            Button(
                onClick = {
                    viewModel.onEvent(AuthEvent.OnCreateUser)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.name.isNotBlank() && state.email.isNotBlank() && state.password.isNotBlank()
            ) {
                Text(
                    text = stringResource(id = R.string.register).uppercase(),
                    color = White,
                    modifier = Modifier.padding(SmallSpace)
                )
            }
            Spacer(modifier = Modifier.height(LargeSpace))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(R.string.already_have_account))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.login),
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navigator.popBackStack(RegisterScreenDestination.route,true)
                        navigator.navigate(LoginScreenDestination())
                    }
                )
            }
        }
    }
}