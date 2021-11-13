package com.omarahmed.shoppinglist.feature_list.presentation.screen_add_item

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.ui.theme.LargeSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.MediumSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SmallSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SuperLargeSpace
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Constants
import com.omarahmed.shoppinglist.core.presentation.component.IconButton
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoilApi
@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
    navController: NavController,
) {
    val state = viewModel.itemNameState.value
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){
            viewModel.onEvent(AddItemEvent.PickImage(it))
        }
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when (event){
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is UiEvent.Navigate -> {
                    navController.popBackStack()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MediumSpace),
    ) {
        Spacer(modifier = Modifier.height(LargeSpace))
        TextField(
            value = state.text ?: "",
            onValueChange = {
                if (it.length <= Constants.MAX_ITEM_NAME_LENGTH){
                    viewModel.onEvent(AddItemEvent.EnteredName(it))
                }
            },
            singleLine = true,
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
                    galleryLauncher.launch("image/*")
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (viewModel.imageUriState.value == null){
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = stringResource(id = R.string.add_item),
                )
                Spacer(modifier = Modifier.height(SmallSpace))
                Text(text = stringResource(id = R.string.click_to_add_item_image))
            } else {
                Box {
                    Image(
                        painter = rememberImagePainter(data = viewModel.imageUriState.value),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier
                        .background(color = Color.DarkGray)
                        .align(Alignment.TopEnd)
                    ) {
                        IconButton(
                            imageVector = Icons.Filled.Cancel ,
                            contentDescription = stringResource(id = R.string.delete_image),
                            onClick = {viewModel.onEvent(AddItemEvent.PickImage(null))}
                        )
                    }

                }

            }

        }
        Spacer(modifier = Modifier.height(SuperLargeSpace))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                viewModel.onEvent(AddItemEvent.SaveItem)
            },
            enabled = !state.text?.trim().isNullOrEmpty()
        ) {
            Text(
                text = stringResource(id = R.string.add),
                color = Color.White,
                modifier = Modifier.padding(SmallSpace)
            )
        }

    }
}