package com.omarahmed.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.omarahmed.shoppinglist.presentation.add_item.AddItemScreen
import com.omarahmed.shoppinglist.presentation.cart.CartScreen
import com.omarahmed.shoppinglist.presentation.home.HomeScreen
import com.omarahmed.shoppinglist.presentation.home.components.BottomBarSection
import com.omarahmed.shoppinglist.presentation.home.components.FabSection
import com.omarahmed.shoppinglist.presentation.home.components.ToolbarSection
import com.omarahmed.shoppinglist.presentation.search.SearchScreen
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
                    topBar = {
                        ToolbarSection(
                            navController = navController,
                            title = when (currentRoute(navController = navController)) {
                                Screens.AddItemScreen.route -> Screens.AddItemScreen.title
                                BottomNavItems.CartScreen.route -> BottomNavItems.CartScreen.title
                                BottomNavItems.HomeScreen.route -> BottomNavItems.HomeScreen.title
                                else -> ""
                            },
                            actionIcon = when (currentRoute(navController = navController)) {
                                Screens.AddItemScreen.route -> null
                                BottomNavItems.CartScreen.route -> Icons.Filled.MoreVert
                                BottomNavItems.HomeScreen.route -> Icons.Filled.Search
                                else -> null
                            },
                            isSearchToolbar = currentRoute(navController = navController) == Screens.SearchScreen.route,
                            showArrowBack = when (currentRoute(navController = navController)) {
                                Screens.AddItemScreen.route -> true
                                Screens.SearchScreen.route -> true
                                else -> false
                            },
                            onActionIconClick = {
                                navController.navigate(Screens.SearchScreen.route)
                            }
                        )
                    },
                    bottomBar = {
                        if (currentRoute(navController = navController) != Screens.AddItemScreen.route) {
                            BottomBarSection(
                                navController = navController,
                                items = listOf(
                                    BottomNavItems.HomeScreen,
                                    BottomNavItems.CartScreen
                                )
                            )

                        }
                    },

                    floatingActionButton = {
                        if (currentRoute(navController = navController) != Screens.AddItemScreen.route) {
                            FabSection(navController)
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItems.HomeScreen.route
                    ) {
                        composable(route = BottomNavItems.HomeScreen.route) {
                            HomeScreen(navController)
                        }
                        composable(route = BottomNavItems.CartScreen.route) {
                            CartScreen()
                        }
                        composable(route = Screens.AddItemScreen.route) {
                            AddItemScreen()
                        }
                        composable(route = Screens.SearchScreen.route) {
                            SearchScreen(navController = navController)
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


