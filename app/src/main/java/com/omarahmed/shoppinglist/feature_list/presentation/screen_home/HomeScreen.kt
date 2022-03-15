package com.omarahmed.shoppinglist.feature_list.presentation.screen_home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.component.TopBarSection
import com.omarahmed.shoppinglist.feature_list.presentation.screen_home.components.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.destinations.SearchScreenDestination
import com.omarahmed.shoppinglist.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.feature_cart.presentation.CartViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import retrofit2.HttpException
import java.io.IOException


@ExperimentalCoilApi
@ExperimentalFoundationApi
@Destination(start = true)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val allItems = homeViewModel.items.collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()

    TopBarSection(
        title = stringResource(id = R.string.home),
        actionIcon = Icons.Default.Search,
        onActionIconClick = {navigator.navigate(SearchScreenDestination())}
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
            ) {
                items(allItems.itemCount) { index ->
                    allItems[index]?.let {item ->
                        ShoppingItem(shoppingItem = item) {
                            homeViewModel.updateItem(item)
                            if (item.isAddedToCart){
                                cartViewModel.insertItem(
                                    CartEntity(
                                        itemName = item.name,
                                        itemIconUrl = item.imageUrl ?: "",
                                        itemId = item.id
                                    )
                                )

                            } else {
                                cartViewModel.deleteItem(item.id)
                            }
                        }
                        if (item.isAddedToCart){
                            cartViewModel.insertItem(
                                CartEntity(
                                    itemName = item.name,
                                    itemIconUrl = item.imageUrl ?: "",
                                    itemId = item.id
                                )
                            )

                        } else {
                            cartViewModel.deleteItem(item.id)
                        }
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


