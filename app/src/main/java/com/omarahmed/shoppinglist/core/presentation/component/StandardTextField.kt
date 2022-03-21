package com.omarahmed.shoppinglist.core.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign

@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    error: String = "",
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (value: String) -> Unit,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    isPasswordVisible: Boolean = false,
    onTrailingIconClick: (Boolean) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxWidth() ) {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            singleLine = singleLine,
            placeholder = {
                Text(text = hint)
            },

            leadingIcon = leadingIcon?.let {
                {
                    Icon(imageVector = it, contentDescription = null)
                }
            },
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = trailingIcon?.let {
                {
                    IconButton(
                        imageVector = it,
                        contentDescription = "",
                        onClick = { onTrailingIconClick(isPasswordVisible) }
                    )
                }
            },
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                color = MaterialTheme.colors.error,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }
    }

}