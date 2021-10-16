package com.omarahmed.shoppinglist.presentation.util

import com.omarahmed.shoppinglist.R

sealed class Screens (val route: String, val title: String) {
    object AddItem: Screens(route = "add_item_screen", title = "Add a new item")
}

sealed class BottomNavItems(val route: String, val title: String, val icon: Int){
    object Home: BottomNavItems(route = "home_screen", title = "Shopping List", icon = R.drawable.ic_home)
    object Cart: BottomNavItems(route = "cart_screen", title = "Cart", icon = R.drawable.ic_cart)
}
