package com.omarahmed.shoppinglist.presentation.shared

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    imageVector: ImageVector,
    contentDescription: String
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    }
}