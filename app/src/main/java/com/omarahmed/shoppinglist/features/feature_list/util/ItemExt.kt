package com.omarahmed.shoppinglist.features.feature_list.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyGridScope
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

@ExperimentalFoundationApi
fun <T:Any>LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyItemScope.(value:T?) -> Unit
){
    items(lazyPagingItems.itemCount) {
        itemContent(lazyPagingItems[it])
    }
}