package com.omarahmed.shoppinglist.feature_list.presentation.components

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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.omarahmed.shoppinglist.core.presentation.ui.theme.GreenAccent
import com.omarahmed.shoppinglist.core.presentation.ui.theme.LargeCornerRadius
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SmallCornerRadius
import com.omarahmed.shoppinglist.core.util.BottomNavItems

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