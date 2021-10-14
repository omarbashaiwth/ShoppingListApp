package com.omarahmed.shoppinglist.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.omarahmed.data.ShoppingItem
import com.omarahmed.shoppinglist.presentation.ui.theme.ImageSize
import com.omarahmed.shoppinglist.presentation.ui.theme.LargeSpace
import com.omarahmed.shoppinglist.presentation.ui.theme.MediumSpace
import com.omarahmed.shoppinglist.presentation.ui.theme.SmallSpace

@Composable
fun ImageWithText(
    item: ShoppingItem
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(
            bottom = LargeSpace * 2,
            top = MediumSpace
        )
    ) {
        Image(
            painter = painterResource(id = item.image),
            contentDescription = item.name,
            modifier = Modifier.size(ImageSize)
        )
        Spacer(modifier = Modifier.height(SmallSpace))
        Text(
            text = item.name,
            style = MaterialTheme.typography.h1,
            modifier = Modifier.fillMaxWidth(),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}