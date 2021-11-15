package com.omarahmed.shoppinglist.core.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.presentation.ui.theme.ImageSize
import com.omarahmed.shoppinglist.core.presentation.ui.theme.LargeSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.MediumSpace
import com.omarahmed.shoppinglist.core.presentation.ui.theme.SmallSpace
import com.omarahmed.shoppinglist.feature_cart.data.entity.CartEntity

@ExperimentalCoilApi
@Composable
fun ImageWithText(
    item: ShoppingItem
) {
    val cartEntity = CartEntity(
        itemName = item.name,
        itemIconUrl = item.imageUrl ?: "",
        itemId = item.id
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            bottom = LargeSpace * 2,
            top = MediumSpace
        )
    ) {
        Image(
            painter = rememberImagePainter(data = cartEntity.itemIconUrl) {
                crossfade(500)
                placeholder(R.drawable.ic_cart)
            },
            contentDescription = cartEntity.itemName,
            modifier = Modifier.size(ImageSize)
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        Text(
            text = cartEntity.itemName,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}