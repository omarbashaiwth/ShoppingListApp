package com.omarahmed.shoppinglist.feature_list.presentation.screen_home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import com.omarahmed.shoppinglist.feature_list.presentation.screen_home.components.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.feature_cart.presentation.CartViewModel
import retrofit2.HttpException
import java.io.IOException


@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState,
    homeViewModel: HomeViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val allItems = homeViewModel.items.collectAsLazyPagingItems()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
                        if (!item.isAddedToCart){
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

                        homeViewModel.updateItem(item)
                    }
                }
            }

        }
        when {
            allItems.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(CenterHorizontally)
                )
            }
            allItems.loadState.append is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                )
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


