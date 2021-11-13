package com.omarahmed.shoppinglist.core.presentation.component

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    imageVector: ImageVector,
    contentDescription: String,
    tintColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
) {
    IconButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(imageVector = imageVector, contentDescription = contentDescription, tint = tintColor )
    }
}