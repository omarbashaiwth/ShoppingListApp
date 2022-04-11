package com.omarahmed.shoppinglist.core.util

import androidx.compose.foundation.ExperimentalFoundationApi
import coil.annotation.ExperimentalCoilApi
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.features.destinations.CartScreenDestination
import com.omarahmed.shoppinglist.features.destinations.DirectionDestination
import com.omarahmed.shoppinglist.features.destinations.HomeScreenDestination

sealed class BottomNavDestinations(
    val destination: DirectionDestination,
    val title: String,
    val icon: Int
) {
    object HomeScreen : BottomNavDestinations(
        destination = HomeScreenDestination,
        title = "Shopping List",
        icon = R.drawable.ic_home
    )

    object CartScreen : BottomNavDestinations(
        destination = CartScreenDestination,
        title = "Cart",
        icon = R.drawable.ic_cart
    )
}
