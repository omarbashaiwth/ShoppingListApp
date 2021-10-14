package com.omarahmed.shoppinglist.presentation.util

import com.omarahmed.shoppinglist.R

sealed class BottomNavItems(var route: String, var title: String, var icon: Int){
    object Home: BottomNavItems(route = "home_screen", title = "Home", icon = R.drawable.ic_home)
    object Cart: BottomNavItems(route = "cart_screen", title = "Cart", icon = R.drawable.ic_cart)
}
