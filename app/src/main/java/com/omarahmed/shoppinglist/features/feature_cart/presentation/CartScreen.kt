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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.component.IconButton
import com.omarahmed.shoppinglist.core.presentation.component.ImageWithText
import com.omarahmed.shoppinglist.core.presentation.component.TopBarSection
import com.omarahmed.shoppinglist.core.presentation.ui.theme.*
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.features.feature_cart.presentation.components.CartItem
import com.omarahmed.shoppinglist.features.feature_cart.presentation.components.StandardAlertDialog
import com.omarahmed.shoppinglist.features.feature_list.presentation.screen_home.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val allCartItems by cartViewModel.allItems
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
        if (allCartItems.isEmpty()) {
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
            items(allCartItems.size) {
                CartItem(
                    cartItem = allCartItems[it],
                    cartViewModel = cartViewModel,
                    homeViewModel = homeViewModel
                )
            }
        }
    }

    if (showDialog) {
       StandardAlertDialog(
           cartViewModel = cartViewModel,
           allCartItems = allCartItems
       )
    }
}

