package com.omarahmed.shoppinglist.features.feature_auth.presentation.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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

@Destination()
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    hideBottomNav: Boolean = true,
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current
    val state by viewModel.authLogin

    LaunchedEffect(key1 = scaffoldState) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }

                is UiEvent.Navigate -> {
                    navigator.popBackStack(
                        route = LoginScreenDestination.route,
                        inclusive = true
                    )
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
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SuperLargeSpace * 2))
            StandardTextField(
                value = state.email,
                hint = stringResource(id = R.string.email),
                onValueChange = {
                    viewModel.onEvent(AuthEvent.OnEmailChanged(it))
                },
                leadingIcon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email,
                keyboardActions = KeyboardActions(
                    onNext = {focusManager.moveFocus(FocusDirection.Down)}
                ),
                imeAction = ImeAction.Next,
                error = when (state.error) {
                    is AuthError.InvalidEmailError -> stringResource(id = R.string.invalid_email_msg)
                    else -> ""
                }
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
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = {viewModel.onEvent(AuthEvent.OnLoginUser)}
                ),
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            )

            Spacer(modifier = Modifier.height(SuperLargeSpace))
            Button(
                onClick = {
                    viewModel.onEvent(AuthEvent.OnLoginUser)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = state.email.isNotBlank() && state.password.isNotBlank()
            ) {
                Text(
                    text = stringResource(id = R.string.login).uppercase(),
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
                Text(text = stringResource(R.string.dont_have_account))
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(R.string.register),
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navigator.popBackStack()
                        navigator.navigate(RegisterScreenDestination())
                    }
                )
            }
        }
    }
}