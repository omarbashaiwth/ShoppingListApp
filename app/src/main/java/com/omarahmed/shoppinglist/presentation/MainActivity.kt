package com.omarahmed.shoppinglist.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.presentation.TestScreen
import com.omarahmed.shoppinglist.presentation.cart.CartScreen
import com.omarahmed.shoppinglist.presentation.home.HomeScreen
import com.omarahmed.shoppinglist.presentation.shared.IconButton
import com.omarahmed.shoppinglist.presentation.ui.theme.*
import com.omarahmed.shoppinglist.presentation.util.BottomNavItems
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
                    topBar = { ToolbarSection() },
                    bottomBar = {
                        if (currentRoute(navController = navController) != "test_screen") {
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
                        if (currentRoute(navController = navController) != "test_screen") {
                            FloatingActionButton(
                                onClick = { /*TODO*/ },
                                shape = CircleShape,
                                backgroundColor = MaterialTheme.colors.primary
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add item",
                                    tint = MaterialTheme.colors.onBackground
                                )
                            }
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
                        composable(route = "test_screen") {
                            TestScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToolbarSection() {
    TopAppBar(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomStart = LargeCornerRadius,
                    bottomEnd = LargeCornerRadius
                )
            )
            .fillMaxWidth(),
        title = {
            Text(
                text = stringResource(id = R.string.title),
                style = MaterialTheme.typography.h1
            )
        },
        actions = {
            IconButton(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        }
    )
}


@Composable
fun BottomBarSection(
    navController: NavController,
    items: List<BottomNavItems>
) {
    BottomAppBar(
        cutoutShape = CircleShape,
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = LargeCornerRadius,
                    topEnd = LargeCornerRadius
                )
            ),
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val curRoute = backStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(SmallCornerRadius))
                            .background(if (curRoute == item.route) GreenAccent else Color.Transparent)
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.title,
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                },
                selected = curRoute == item.route,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}

@Composable
 fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}


