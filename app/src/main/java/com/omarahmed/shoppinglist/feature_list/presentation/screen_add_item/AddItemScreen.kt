package com.omarahmed.shoppinglist.feature_list.presentation.screen_add_item

import android.widget.Space
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.ui.theme.LargeSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.MediumSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SmallSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SuperLargeSpace

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel()
) {
    val state = viewModel.itemNameState.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MediumSpace),
    ) {
        Spacer(modifier = Modifier.height(LargeSpace))
        TextField(
            value = state.text ?: "",
            onValueChange = {
                viewModel.onEvent(AddItemEvent.EnteredName(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.item_name))
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(LargeSpace))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.4f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = MaterialTheme.shapes.large
                )
                .clip(MaterialTheme.shapes.large)
                .clickable {

                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = stringResource(id = R.string.add_item),
            )
            Spacer(modifier = Modifier.height(SmallSpace))
            Text(text = stringResource(id = R.string.click_to_add_item_image))
        }
        Spacer(modifier = Modifier.height(SuperLargeSpace))
        Button(
            modifier = Modifier.padding(SmallSpace).fillMaxWidth(),
            onClick = {
                viewModel.onEvent(AddItemEvent.SaveItem)
            },
            enabled = !state.text?.trim().isNullOrEmpty()
        ) {
            Text(
                text = stringResource(id = R.string.add),
                color = Color.White
            )
        }

    }
}