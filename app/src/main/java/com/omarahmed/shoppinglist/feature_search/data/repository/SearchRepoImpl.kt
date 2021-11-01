package com.omarahmed.shoppinglist.feature_search.data.repository

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.feature_search.domain.repository.SearchRepo
import retrofit2.HttpException
import java.io.IOException

class SearchRepoImpl(
    private val api: ShoppingListApi
): SearchRepo {
    override suspend fun getSearchResult(query: String?): Resource<List<ShoppingItem>> {
        return try {
            query?.let {
                val result = api.searchForItem(query)
                if (result.isNotEmpty()){
                    Resource.Success(result)
                } else {
                    Resource.Error("No result..Try searching using another query")
                }
            } ?: Resource.Error("Please, write something to search")



        } catch (e: IOException) {
            Resource.Error("Couldn't reach the server. Check your Internet connection")
        } catch (e: HttpException) {
            Resource.Error("Something went wrong. Please try again later")
        }
    }
}