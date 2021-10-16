package com.omarahmed.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.presentation.add_item.AddItemScreen
import com.omarahmed.shoppinglist.presentation.cart.CartScreen
import com.omarahmed.shoppinglist.presentation.home.HomeScreen
import com.omarahmed.shoppinglist.presentation.home.components.BottomBarSection
import com.omarahmed.shoppinglist.presentation.home.components.FabSection
import com.omarahmed.shoppinglist.presentation.home.components.ToolbarSection
import com.omarahmed.shoppinglist.presentation.shared.IconButton
import com.omarahmed.shoppinglist.presentation.ui.theme.*
import com.omarahmed.shoppinglist.presentation.util.BottomNavItems
import com.omarahmed.shoppinglist.presentation.util.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                val navController = rememberNavController()

//                val systemUiController = rememberSystemUiController()
//                SideEffect {
//                    systemUiController.setStatusBarColor(color = DarkGray)
//                }
                Scaffold(
                    backgroundColor = MaterialTheme.colors.background,
                    topBar = { ToolbarSection(
                        navController = navController,
                        title = when(currentRoute(navController = navController)){
                            Screens.AddItem.route  -> Screens.AddItem.title
                            BottomNavItems.Cart.route  -> BottomNavItems.Cart.title
                            BottomNavItems.Home.route  -> BottomNavItems.Home.title
                            else -> ""
                        },
                        actionIcon = when(currentRoute(navController = navController)){
                            Screens.AddItem.route  -> null
                            BottomNavItems.Cart.route  -> Icons.Filled.MoreVert
                            BottomNavItems.Home.route  -> Icons.Filled.Search
                            else -> null
                        },
                        showArrowBack = currentRoute(navController = navController) == Screens.AddItem.route
                    ) },
                    bottomBar = {
                        if (currentRoute(navController = navController) != Screens.AddItem.route) {
                            BottomBarSection(
                                navController = navController,
                                items = listOf(
                                    BottomNavItems.Home,
                                    BottomNavItems.Cart
                                )
                            )
                        }

                    },

                    floatingActionButton = {
                        if (currentRoute(navController = navController) != Screens.AddItem.route) {
                            FabSection(navController)
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItems.Home.route
                    ) {
                        composable(route = BottomNavItems.Home.route) {
                            HomeScreen(navController)
                        }
                        composable(route = BottomNavItems.Cart.route) {
                            CartScreen()
                        }
                        composable(route = Screens.AddItem.route) {
                            AddItemScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
 fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


