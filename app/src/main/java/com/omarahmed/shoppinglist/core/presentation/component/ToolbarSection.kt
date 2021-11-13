package com.omarahmed.shoppinglist.core.presentation.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.omarahmed.shoppinglist.R
import com.omarahmed.shoppinglist.core.presentation.ui.theme.LargeCornerRadius

@Composable
fun ToolbarSection(
    navController: NavController,
    title: String,
    actionIcon: ImageVector?,
    showToolbar: Boolean,
    showArrowBack: Boolean,
    onActionIconClick: () -> Unit = {},
) {
    if (showToolbar) {
        TopAppBar(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        bottomStart = LargeCornerRadius,
                        bottomEnd = LargeCornerRadius
                    )
                )
                .fillMaxWidth(),
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h1
                )
            },
            actions = {
                actionIcon?.let {
                    IconButton(
                        imageVector = it,
                        contentDescription = title,
                        onClick = onActionIconClick
                    )
                }

            },
            navigationIcon = if (showArrowBack) {
                {
                    IconButton(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        onClick = { navController.popBackStack() }
                    )
                }
            } else null

        )
    }
}
