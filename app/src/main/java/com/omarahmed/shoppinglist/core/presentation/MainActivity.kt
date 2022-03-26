package com.omarahmed.shoppinglist.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.omarahmed.shoppinglist.core.presentation.component.BottomBarSection
import com.omarahmed.shoppinglist.core.presentation.component.FabSection
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.core.util.BottomNavDestinations
import com.omarahmed.shoppinglist.features.NavGraphs
import com.omarahmed.shoppinglist.features.destinations.AddItemScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigateTo
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalCoilApi
@AndroidEntryPoint
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingListTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val hideBottomNav = navBackStackEntry?.arguments?.getBoolean(ARG_BOTTOM_NAV_VISIBILITY)

                Scaffold(
                    backgroundColor = MaterialTheme.colors.background,
                    bottomBar = {
                        if (hideBottomNav == null || !hideBottomNav){
                            BottomBarSection(
                                navController = navController,
                                backStackEntry = navBackStackEntry,
                                destinations = listOf(
                                    BottomNavDestinations.HomeScreen,
                                    BottomNavDestinations.CartScreen
                                )
                            )
                        }
                    },
                    floatingActionButton = {
                        if (hideBottomNav == null || !hideBottomNav){
                            FabSection(
                                onFabClick = {navController.navigateTo(AddItemScreenDestination())},
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true
                ) {
                    DestinationsNavHost(navGraph = NavGraphs.root,  navController = navController)
                }
            }
        }
    }
}

private const val ARG_BOTTOM_NAV_VISIBILITY = "hideBottomNav"


