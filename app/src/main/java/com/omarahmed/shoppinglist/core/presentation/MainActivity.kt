package com.omarahmed.shoppinglist.core.presentation

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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.omarahmed.shoppinglist.feature_list.presentation.screen_add_item.AddItemScreen
import com.omarahmed.shoppinglist.presentation.cart.CartScreen
import com.omarahmed.shoppinglist.feature_list.presentation.screen_home.HomeScreen
import com.omarahmed.shoppinglist.core.presentation.component.BottomBarSection
import com.omarahmed.shoppinglist.core.presentation.component.FabSection
import com.omarahmed.shoppinglist.core.presentation.component.ToolbarSection
import com.omarahmed.shoppinglist.feature_search.presentation.SearchScreen
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.core.util.BottomNavItems
import com.omarahmed.shoppinglist.core.util.Screens
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
//                val systemUiController = rememberSystemUiController()
//                SideEffect {
//                    systemUiController.setStatusBarColor(color = DarkGray)
//                }
                Scaffold(
                    scaffoldState = scaffoldState,
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
                            showToolbar = currentRoute(navController = navController) != Screens.SearchScreen.route,
                            showArrowBack = when (currentRoute(navController = navController)) {
                                Screens.AddItemScreen.route -> true
                                else -> false
                            },
                            onActionIconClick = {
                                navController.navigate(Screens.SearchScreen.route)
                            }
                        )
                    },
                    bottomBar = {
                        BottomBarSection(
                            showBottomBar = when (currentRoute(navController = navController)) {
                                Screens.AddItemScreen.route -> false
                                Screens.SearchScreen.route -> false
                                else -> true
                            },
                            navController = navController,
                            items = listOf(
                                BottomNavItems.HomeScreen,
                                BottomNavItems.CartScreen
                            )
                        )
                    },

                    floatingActionButton = {
                        FabSection(
                            navController = navController,
                            showFabButton = when (currentRoute(navController = navController)) {
                                Screens.AddItemScreen.route -> false
                                Screens.SearchScreen.route -> false
                                else -> true
                            }
                        )

                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = BottomNavItems.HomeScreen.route
                    ) {
                        composable(route = BottomNavItems.HomeScreen.route) {
                            HomeScreen(scaffoldState)
                        }
                        composable(route = BottomNavItems.CartScreen.route) {
                            CartScreen()
                        }
                        composable(route = Screens.AddItemScreen.route) {
                            AddItemScreen()
                        }
                        composable(route = Screens.SearchScreen.route) {
                            SearchScreen(scaffoldState = scaffoldState){
                                navController.popBackStack()
                            }
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


