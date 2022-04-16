package com.omarahmed.shoppinglist.features.feature_cart.presentation.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.features.feature_cart.data.entity.CartEntity
import com.omarahmed.shoppinglist.features.feature_cart.presentation.CartEvent
import com.omarahmed.shoppinglist.features.feature_cart.presentation.CartViewModel

@Composable
fun StandardAlertDialog(
    cartViewModel: CartViewModel,
    allCartItems: List<CartEntity>
) {
    AlertDialog(
        onDismissRequest = { cartViewModel.onEvent(CartEvent.OnDeleteAllDismissed) },
        title = {
            Text(
                text = if (allCartItems.isNotEmpty()) stringResource(R.string.delete_all_title) else stringResource(
                    R.string.empty_cart_title
                )
            )
        },
        text = {
            Text(
                text = if (allCartItems.isNotEmpty()) stringResource(R.string.delete_all_text) else stringResource(
                    R.string.empty_cart_text
                )
            )
        },
        confirmButton = {
            if (allCartItems.isNotEmpty()) {
                TextButton(
                    onClick = {
                        cartViewModel.onEvent(CartEvent.OnDeleteAllConfirmed(
                            ids = allCartItems.map {
                                it.itemId
                            }
                        ))
                    }
                ) {
                    Text(text = stringResource(R.string.confirm))
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { cartViewModel.onEvent(CartEvent.OnDeleteAllDismissed) }) {
                Text(text = stringResource(R.string.dismiss))
            }
        }
    )
}