package com.omarahmed.shoppinglist.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.omarahmed.data.ShoppingItem
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.presentation.shared.IconButton
import com.omarahmed.shoppinglist.presentation.shared.ImageWithText
import com.omarahmed.shoppinglist.presentation.ui.theme.*

@Composable
fun CartScreen() {
    val cartItems = listOf(
        ShoppingItem("Tomato", R.drawable.tomato),
        ShoppingItem("Garlic", R.drawable.garlic),
        ShoppingItem("Chicken", R.drawable.chicken),
        ShoppingItem("Rice", R.drawable.rice)
    )
    LazyColumn(
        contentPadding = PaddingValues(
            bottom = SuperLargeSpace,
            start = SmallSpace,
            end = SmallSpace,
            top = SmallSpace
        )
    ) {
        items(cartItems.size) {
            CartItem(cartItem = cartItems[it])
        }
    }
}

@Composable
fun CartItem(cartItem: ShoppingItem) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(SmallSpace)
            .fillMaxWidth()
            .clip(RoundedCornerShape(LargeCornerRadius))
            .background(MaterialTheme.colors.surface)
    ) {
        ImageWithText(cartItem)
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
                imageVector = Icons.Default.CheckCircle,
                contentDescription = stringResource(id = R.string.already_bought)
            )
            IconButton(
                modifier = Modifier.size(ButtonHeight),
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(id = R.string.remove)
            )
        }
    }

}

