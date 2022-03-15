package com.omarahmed.shoppinglist.core.util

import androidx.compose.foundation.ExperimentalFoundationApi
import coil.annotation.ExperimentalCoilApi
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.destinations.*

sealed class BottomNavDestinations(
    val destination: DirectionDestination,
    val title: String,
    val icon: Int
) {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class)
    object HomeScreen : BottomNavDestinations(
        destination = HomeScreenDestination,
        title = "Shopping List",
        icon = R.drawable.ic_home
    )

    @OptIn(ExperimentalCoilApi::class)
    object CartScreen : BottomNavDestinations(
        destination = CartScreenDestination,
        title = "Cart",
        icon = R.drawable.ic_cart
    )
}
