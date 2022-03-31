package com.omarahmed.shoppinglist.features.feature_cart.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.component.IconButton
import com.omarahmed.shoppinglist.core.presentation.component.ImageWithText
import com.omarahmed.shoppinglist.core.presentation.component.TopBarSection
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.features.feature_list.presentation.screen_home.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allItems by cartViewModel.allItems
    val showDialog by cartViewModel.showDialog
    var menuExpanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBarSection(
            title = stringResource(id = R.string.cart),
            showMenuActionIcon = true,
            onMenuIconClick = { menuExpanded = true },
            dropDownMenu = {
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(
                        onClick = {
                            menuExpanded = false
                            cartViewModel.onDeleteAllClicked()
                        }
                    ) {
                        Text(text = stringResource(R.string.delete_all_items))
                    }
                }
            }
        )
        if (allItems.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_cart),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(SmallSpace))
                Text(text = stringResource(R.string.empty_cart))
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(
                bottom = SuperLargeSpace,
                start = SmallSpace,
                end = SmallSpace,
                top = SmallSpace
            )
        ) {
            items(allItems.size) {
                CartItem(
                    cartItem = allItems[it],
                    cartViewModel = cartViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { cartViewModel.onDeleteAllDismissed() },
            title = { Text("Delete all items") },
            text = { Text("Are you sure you want to delete all items from cart?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        cartViewModel.onDeleteAllConfirmed()
                    }
                ) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { cartViewModel.onDeleteAllDismissed() }) {
                    Text(text = "Dismiss")
                }
            }
        )
    }
}

@Composable
fun CartItem(
    cartItem: CartEntity,
    cartViewModel: CartViewModel,
    homeViewModel: HomeViewModel
) {

    val shoppingItem = ShoppingItem(
        name = cartItem.itemName,
        imageUrl = cartItem.itemIconUrl,
        id = cartItem.itemId
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(SmallSpace)
            .fillMaxWidth()
            .clip(RoundedCornerShape(LargeCornerRadius))
            .background(MaterialTheme.colors.surface)
    ) {
        ImageWithText(shoppingItem)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        bottomStart = LargeCornerRadius,
                        bottomEnd = LargeCornerRadius
                    )
                )
                .background(MaterialTheme.colors.primary)
                .align(Alignment.BottomCenter)
                .padding(horizontal = SuperLargeSpace)
        ) {
            IconButton(
                modifier = Modifier.size(ButtonHeight),
                tintColor = if (cartItem.isBought) Color.Yellow else LocalContentColor.current.copy(
                    alpha = LocalContentAlpha.current
                ),
                imageVector = Icons.Default.CheckCircle,
                contentDescription = stringResource(id = R.string.already_bought),
                onClick = {
                    cartViewModel.onBoughtStateChange(cartItem)
                }
            )
            IconButton(
                modifier = Modifier.size(ButtonHeight),
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.remove),
                onClick = {
                    cartViewModel.deleteItem(cartItem.itemId)
                    homeViewModel.updateItem(
                        cartItem.itemId,
                        false,
                    )
                }
            )
        }
    }
}

