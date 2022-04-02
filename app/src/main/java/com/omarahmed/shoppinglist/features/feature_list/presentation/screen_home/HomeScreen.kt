package com.omarahmed.shoppinglist.features.feature_list.presentation.screen_home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.component.TopBarSection
import com.omarahmed.shoppinglist.features.feature_list.presentation.screen_home.components.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.core.presentation.util.UiEvent
import com.omarahmed.shoppinglist.features.destinations.HomeScreenDestination
import com.omarahmed.shoppinglist.features.destinations.SearchScreenDestination
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.features.feature_cart.presentation.CartViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import retrofit2.HttpException
import java.io.IOException


@Destination()
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val allItems = homeViewModel.items.collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit){
        homeViewModel.events.collectLatest { event ->
            when(event){
                is UiEvent.Navigate -> {
                    navigator.popBackStack(HomeScreenDestination.route, true)
                    navigator.navigate(event.destination,)
                }
                is UiEvent.ShowSnackbar -> Unit
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopBarSection(
            title = stringResource(id = R.string.home),
            showSearchActionIcon = true,
            showMenuActionIcon = true,
            onSearchIconClick = {navigator.navigate(SearchScreenDestination())},
            onMenuIconClick = { menuExpanded = true},
            dropDownMenu = {
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            menuExpanded = false
                            cartViewModel.deleteAllItems(
                                ids =  allItems.itemSnapshotList.map {
                                    it?.id ?: ""
                                }
                            )
                            homeViewModel.logout()
                        }
                    ) {
                        Text(text = stringResource(R.string.logout))
                    }
                }
            }
        )
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = allItems.loadState.refresh is LoadState.Loading),
            onRefresh = {allItems.refresh()},
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    bottom = SuperLargeSpace,
                    start = SmallSpace,
                    end = SmallSpace,
                    top = SmallSpace
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                items(allItems.itemCount) { index ->
                    allItems[index]?.let {item ->
                        ShoppingItem(
                            shoppingItem = item,
                            onAddItemClick = {
                                if (!item.isAddedToCart){
                                    cartViewModel.insertItem(
                                        CartEntity(
                                            itemName = item.name,
                                            itemIconUrl = item.imageUrl ?: "",
                                            itemId = item.id
                                        )
                                    )
                                    homeViewModel.updateItem(item.id,true)

                                } else {
                                    cartViewModel.deleteItem(item.id)
                                    homeViewModel.updateItem(item.id,false)
                                }
                            }
                        )
//                        if (item.isAddedToCart){
//                            Log.d("HomeScreen", "isAddedToCart")
//                            cartViewModel.insertItem(
//                                CartEntity(
//                                    itemName = item.name,
//                                    itemIconUrl = item.imageUrl ?: "",
//                                    itemId = item.id
//                                )
//                            )
//
//                        } else {
//                            Log.d("HomeScreen", "isNotAddedToCart")
//                            cartViewModel.deleteItem(item.id)
//                        }
                    }
                }

            }
        }
        
        when {
            allItems.loadState.append is LoadState.Loading -> {
                CircularProgressIndicator()
            }
            allItems.loadState.refresh is LoadState.Error -> {
                LaunchedEffect(key1 = true) {
                    val e = allItems.loadState.refresh as LoadState.Error
                    val msg = when(e.error){
                        is IOException -> "Couldn't reach the server. Check your Internet connection"
                        is HttpException -> "Something went wrong. Please, try again later"
                        else -> "Unknown error occurred"
                    }
                    scaffoldState.snackbarHostState.showSnackbar(message = msg)
                }
            }
        }
    }
}


