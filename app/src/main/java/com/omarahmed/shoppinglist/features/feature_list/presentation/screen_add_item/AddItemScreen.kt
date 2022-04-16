package com.omarahmed.shoppinglist.features.feature_list.presentation.screen_add_item

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.core.util.Constants
import com.omarahmed.shoppinglist.core.presentation.component.IconButton
import com.omarahmed.shoppinglist.core.presentation.component.TopBarSection
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.features.destinations.HomeScreenDestination
import com.omarahmed.shoppinglist.features.destinations.IconSearchScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    selectedIconUrl: String = "",
    hideBottomNav: Boolean = true,
) {
    val scaffoldState = rememberScaffoldState()
    val state by viewModel.addItem
//    val galleryLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()){
//            viewModel.onEvent(AddItemEvent.PickIcon(it))
//        }
    LaunchedEffect(key1 = true) {
        viewModel.onEvent(AddItemEvent.PickIcon(selectedIconUrl))
        viewModel.events.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is UiEvent.Navigate -> {
                    navigator.popBackStack()
                }
            }
        }
    }
    TopBarSection(
        title = stringResource(id = R.string.add_item),
        navigationIcon = Icons.Default.ArrowBack,
        onArrowBackClick = { navigator.popBackStack() }
    )

    Column(
        modifier = Modifier.padding(MediumSpace),
    ) {

        Spacer(modifier = Modifier.height(SuperLargeSpace))
        TextField(
            value = state.itemName,
            onValueChange = {
                if (it.length <= Constants.MAX_ITEM_NAME_LENGTH) {
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
                    navigator.popBackStack()
                    navigator.navigate(IconSearchScreenDestination())
//                    galleryLauncher.launch("image/*")
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.iconUrl.isBlank()) {
                Icon(
                    imageVector = Icons.Filled.AddCircle,
                    contentDescription = stringResource(id = R.string.add_item),
                )
                Spacer(modifier = Modifier.height(SmallSpace))
                Text(text = stringResource(id = R.string.click_to_add_item_image))
            } else {
                Box {
                    Image(
                        painter = rememberImagePainter(data = state.iconUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .background(color = Color.DarkGray)
                            .align(Alignment.TopEnd)
                    ) {
                        IconButton(
                            imageVector = Icons.Filled.Cancel,
                            contentDescription = stringResource(id = R.string.delete_image),
                            onClick = { viewModel.onEvent(AddItemEvent.PickIcon("")) }
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
                navigator.popBackStack()
                navigator.navigate(HomeScreenDestination())
            },
            enabled = state.itemName.trim().isNotEmpty()
        ) {
            Text(
                text = stringResource(id = R.string.add),
                color = Color.White,
                modifier = Modifier.padding(SmallSpace)
            )
        }

    }
}