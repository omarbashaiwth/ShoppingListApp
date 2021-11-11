package com.omarahmed.shoppinglist.feature_list.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.util.Constants
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ItemsSource @Inject constructor(
    private val api: ShoppingListApi
): PagingSource<Int,ShoppingItem>() {
    private var currentPage = 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShoppingItem> {
        return try {
            val page = params.key ?: currentPage
            val itemList = api.getAllItems(
                page = page,
                pageSize = Constants.PAGE_SIZE
            )
            LoadResult.Page(
                data = itemList,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (itemList.isEmpty()) null else currentPage + 1
            ).also { currentPage++ }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e:HttpException){
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, ShoppingItem>): Int? {
            return state.anchorPosition
    }


}