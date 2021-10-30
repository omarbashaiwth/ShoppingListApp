package com.omarahmed.shoppinglist.feature_list.presentation.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.feature_search.presentation.SearchViewModel
import com.omarahmed.shoppinglist.presentation.shared.IconButton
import com.omarahmed.shoppinglist.core.presentation.ui.theme.LargeCornerRadius

@Composable
fun ToolbarSection(
    navController: NavController,
    title: String,
    actionIcon: ImageVector?,
    showArrowBack: Boolean,
    isSearchToolbar: Boolean,
    onActionIconClick: () -> Unit = {},
    searchViewModel: SearchViewModel = hiltViewModel()
) {

    TopAppBar(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomStart = LargeCornerRadius,
                    bottomEnd = LargeCornerRadius
                )
            )
            .fillMaxWidth(),
        title = {
            if (isSearchToolbar) {
                SearchTextField(searchViewModel = searchViewModel)
            } else {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h1
                )
            }

        },
        actions = {
            actionIcon?.let {
                IconButton(
                    imageVector = it,
                    contentDescription = title,
                    onClick = onActionIconClick
                )
            }

        },
        navigationIcon = if (showArrowBack) {
            {
                IconButton(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    onClick = { navController.popBackStack() }
                )
            }
        } else null

    )

}

@Composable
fun SearchTextField(searchViewModel: SearchViewModel) {
    val focusRequester = remember {
        FocusRequester()
    }
    TextField(
        value = searchViewModel.querySearchState.value,
        onValueChange = { searchViewModel.setQuery(it) },
        shape = RectangleShape,
        placeholder = {
            Text(text = stringResource(id = R.string.search))
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
        ,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
    )
    DisposableEffect(Unit){
        focusRequester.requestFocus()
        onDispose {  }
    }
}