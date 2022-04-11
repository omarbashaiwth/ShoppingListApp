package com.omarahmed.shoppinglist.features.feature_cart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.component.IconButton
import com.omarahmed.shoppinglist.core.presentation.component.ImageWithText
import com.omarahmed.shoppinglist.core.presentation.ui.theme.ButtonHeight
import com.omarahmed.shoppinglist.core.presentation.ui.theme.LargeCornerRadius
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SmallSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SuperLargeSpace
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.features.feature_cart.presentation.CartViewModel
import com.omarahmed.shoppinglist.features.feature_list.presentation.screen_home.HomeViewModel

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