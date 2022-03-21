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
fun TopBarSection(
    modifier: Modifier = Modifier,
    title: String,
    actionIcon: ImageVector? = null,
    navigationIcon: ImageVector? = null,
    onActionIconClick: () -> Unit = {},
    onArrowBackClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier
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
                style = MaterialTheme.typography.h2
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
        navigationIcon = navigationIcon?.let {
            {
                IconButton(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    onClick = onArrowBackClick
                )
            }
        }
    )
}
