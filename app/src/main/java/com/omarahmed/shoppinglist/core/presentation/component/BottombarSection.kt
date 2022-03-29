package com.omarahmed.shoppinglist.core.presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.omarahmed.shoppinglist.core.presentation.ui.theme.GreenAccent
import com.omarahmed.shoppinglist.core.presentation.ui.theme.LargeCornerRadius
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SmallCornerRadius
import com.omarahmed.shoppinglist.core.util.BottomNavDestinations
import com.omarahmed.shoppinglist.features.navDestination
import com.ramcosta.composedestinations.navigation.navigateTo

@Composable
fun BottomBarSection(
    navController: NavController,
    backStackEntry: NavBackStackEntry?,
    destinations: List<BottomNavDestinations>
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
        val curDestination = backStackEntry?.navDestination
        destinations.forEach { destination ->
            BottomNavigationItem(
                selected = curDestination == destination.destination,
                onClick = {
                    navController.navigateTo(destination.destination) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(SmallCornerRadius))
                            .background(if (curDestination == destination.destination) GreenAccent else Color.Transparent)
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = destination.icon),
                            contentDescription = destination.title,
                            tint = MaterialTheme.colors.onBackground
                        )
                    }
                }
            )
        }
    }
}