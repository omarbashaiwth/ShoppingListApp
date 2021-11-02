package com.omarahmed.shoppinglist.feature_list.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.util.Constants
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.feature_list.data.paing.ItemsSource
import com.omarahmed.shoppinglist.feature_list.domain.repository.ShoppingListRepo
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShoppingLisRepoImpl @Inject constructor(
    private val api: ShoppingListApi
) : ShoppingListRepo {

    override val allItems: Flow<PagingData<ShoppingItem>>
        get() = Pager(
            PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = true,
            )
        ) {
            ItemsSource(api)
        }.flow
}