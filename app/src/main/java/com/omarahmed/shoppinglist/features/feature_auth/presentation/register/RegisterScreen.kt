package com.omarahmed.shoppinglist.features.feature_auth.presentation.register

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.omarahmed.shoppinglist.features.feature_auth.presentation.util.AuthError
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination()
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    hideBottomNav: Boolean = true,
) {
    val scaffoldState = rememberScaffoldState()
    val focusManager = LocalFocusManager.current
    val nameState by viewModel.name
    val emailState by viewModel.email
    val passwordState by viewModel.password
    val loading by viewModel.loading

    LaunchedEffect(key1 = Unit){
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
        if (loading){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        Column(
            modifier = Modifier
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
                modifier = Modifier.padding(end = 30.dp).size(150.dp)
            )
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.h1
            )
            Spacer(modifier = Modifier.height(SuperLargeSpace * 2))
            StandardTextField(
                value = nameState.text,
                hint = stringResource(id = R.string.name),
                onValueChange = {
                    if (it.length <= 20) {
                        viewModel.onNameChange(it)
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
                value = emailState.text,
                hint = stringResource(id = R.string.email),
                onValueChange = {
                    viewModel.onEmailChange(it)
                },
                leadingIcon = Icons.Filled.Email,
                keyboardType = KeyboardType.Email,
                error = when (emailState.error) {
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
                value = passwordState.text,
                hint = stringResource(id = R.string.password),
                onValueChange = {
                    viewModel.onPasswordChange(it)
                },
                leadingIcon = Icons.Filled.Lock,
                trailingIcon = if (passwordState.isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                isPasswordVisible = passwordState.isPasswordVisible,
                onTrailingIconClick = {
                    viewModel.onPasswordToggleVisibility(it)
                },
                visualTransformation = if (passwordState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                error = when (passwordState.error) {
                    is AuthError.TooShortInputError -> stringResource(id = R.string.too_short_password_msg)
                    is AuthError.InvalidPasswordError -> stringResource(id = R.string.invalid_password_msg)
                    else -> ""
                },
                imeAction = ImeAction.Done,
                keyboardActions = KeyboardActions(
                    onDone = {viewModel.onCreateUser()}
                )
            )
            Spacer(modifier = Modifier.height(SuperLargeSpace))
            Button(
                onClick = {
                    viewModel.onCreateUser()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = nameState.text.isNotBlank() && emailState.text.isNotBlank() && passwordState.text.isNotBlank()
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