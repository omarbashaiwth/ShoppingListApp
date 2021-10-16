package com.omarahmed.shoppinglist.presentation.home.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.omarahmed.shoppinglist.presentation.util.Screens

@Composable
fun FabSection(navController: NavController) {
    FloatingActionButton(
        onClick = { navController.navigate(Screens.AddItem.route)},
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