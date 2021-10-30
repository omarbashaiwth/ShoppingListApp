package com.omarahmed.shoppinglist.core.util

import com.omarahmed.shoppinglist.R

sealed class Screens (val route: String, val title: String) {
    object AddItemScreen: Screens(route = "add_item_screen", title = "Add a new item")
    object SearchScreen: Screens(route = "search_screen", title = "Search")
}

sealed class BottomNavItems(val route: String, val title: String, val icon: Int){
    object HomeScreen: BottomNavItems(route = "home_screen", title = "Shopping List", icon = R.drawable.ic_home)
    object CartScreen: BottomNavItems(route = "cart_screen", title = "Cart", icon = R.drawable.ic_cart)
}
