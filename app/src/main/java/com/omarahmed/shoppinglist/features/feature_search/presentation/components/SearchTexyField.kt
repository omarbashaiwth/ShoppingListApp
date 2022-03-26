package com.omarahmed.shoppinglist.features.feature_search.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.features.feature_search.presentation.SearchEvent
import com.omarahmed.shoppinglist.features.feature_search.presentation.SearchViewModel
import com.omarahmed.shoppinglist.core.presentation.component.IconButton

@Composable
fun SearchTextField(
    searchQuery: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.back),
            onClick = { onBackClick() }
        )

        TextField(
            value = searchQuery,
            onValueChange = {
                onSearchQueryChange(it)
            },
            shape = RectangleShape,
            placeholder = {
                Text(text = stringResource(id = R.string.search))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(searchQuery)
                focusManager.clearFocus()

            }),
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                backgroundColor = MaterialTheme.colors.surface
            )
        )
        DisposableEffect(Unit) {
            focusRequester.requestFocus()
            onDispose { }
        }
    }

}