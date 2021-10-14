package com.omarahmed.shoppinglist.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.omarahmed.data.ShoppingItem
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.presentation.shared.IconButton
import com.omarahmed.shoppinglist.presentation.shared.ImageWithText
import com.omarahmed.shoppinglist.presentation.ui.theme.*


@ExperimentalFoundationApi
@Composable
fun HomeScreen(navController: NavController) {
    val shoppingList = listOf(
        ShoppingItem("Tomato", R.drawable.tomato),
        ShoppingItem("Garlic", R.drawable.garlic),
        ShoppingItem("Chicken", R.drawable.chicken),
        ShoppingItem("Rice", R.drawable.rice),
        ShoppingItem("Flour"),
        ShoppingItem("Sugar"),
        ShoppingItem("Tea"),
        ShoppingItem("Potato"),
        ShoppingItem("Tomato"),
        ShoppingItem("Tomato"),
    )
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            bottom = SuperLargeSpace,
            start = SmallSpace,
            end = SmallSpace,
            top = SmallSpace
        ),
    ) {
        items(shoppingList.size) {
            ShoppingItem(shoppingItem = shoppingList[it], navController)
        }
    }
}

@Composable
fun ShoppingItem(
    shoppingItem: ShoppingItem,
    navController: NavController
) {
    var isAddedToCart by remember {
        mutableStateOf(false)
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(SmallSpace)
            .clip(
                RoundedCornerShape(
                    topStart = LargeCornerRadius,
                    bottomEnd = LargeCornerRadius
                )
            )
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
    ) {
        ImageWithText(item = shoppingItem)
        IconButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(bottomEnd = LargeCornerRadius))
                .background(if (isAddedToCart) Color.Red else MaterialTheme.colors.primary)
                .size(ButtonHeight),
            onClick = {
                isAddedToCart = !isAddedToCart
                navController.navigate("test_screen")
            },
            imageVector = if (isAddedToCart) Icons.Default.Check else Icons.Default.Add,
            contentDescription = if (isAddedToCart) stringResource(id = R.string.remove) else stringResource(
                id = R.string.add_to_cart
            )
        )

    }

}
