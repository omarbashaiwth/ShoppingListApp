package com.omarahmed.shoppinglist.features.feature_icon_search.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SmallSpace
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.features.destinations.AddItemScreenDestination
import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response.Format
import com.omarahmed.shoppinglist.features.feature_search.presentation.components.SearchTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@Destination
@Composable
fun IconSearchScreen(
    viewModel: IconSearchViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    hideBottomNav: Boolean = true
) {
    val state by viewModel.iconSearch
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = scaffoldState){
        viewModel.events.collectLatest { event ->
            when(event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is UiEvent.Navigate -> Unit
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchTextField(
            searchQuery = state.iconSearchQuery,
            onSearchQueryChange = {viewModel.onEvent(IconSearchEvent.OnIconQueryChange(it))},
            onSearch = {viewModel.onEvent(IconSearchEvent.OnIconSearch(it))},
            onBackClick = { navigator.popBackStack() }
        )
        if (state.isLoading){
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        if (state.iconSearchResult?.totalCount == 0){
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.SearchOff,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp)
                )
                Text(text = "No result found, try another query")
            }
        }
        LazyVerticalGrid(cells = GridCells.Fixed(2)){
            val response = state.iconSearchResult?.icons ?: emptyList()
            items(response.size){
                Log.d("IconSearchScreen",response.size.toString())
                val listFormat = mutableListOf<Format>()
                val rasterSize = response[it].rasterSizes.filter { it.size == 128 }
                if (rasterSize.isNotEmpty()){
                    val format = rasterSize[0].formats.filter { it.format == "png" }
                    listFormat += format
                }

                Image(
                    painter = rememberImagePainter(data = if (listFormat.toList().isNotEmpty()) listFormat.toList()[0].previewUrl else null),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(SmallSpace)
                        .size(100.dp)
                        .clickable {
                            navigator.popBackStack()
                            navigator.navigate(AddItemScreenDestination(selectedIconUrl = listFormat.toList()[0].previewUrl))
                        }
                )
            }
        }

    }
}