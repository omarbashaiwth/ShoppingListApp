package com.omarahmed.shoppinglist.core.presentation.component


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
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
    navigationIcon: ImageVector? = null,
    onArrowBackClick: () -> Unit = {},
    showMenuActionIcon: Boolean = false,
    showSearchActionIcon:Boolean = false,
    onSearchIconClick: () -> Unit = {},
    onMenuIconClick: () -> Unit = {},
    dropDownMenu: @Composable () -> Unit = {}
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
            if (showSearchActionIcon) {
                IconButton(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    onClick = onSearchIconClick
                )
            }
            if (showMenuActionIcon)
                IconButton(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_oprions),
                    onClick = onMenuIconClick
                )
            dropDownMenu()
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
