package com.omarahmed.shoppinglist.features.feature_search.data.repository

import com.omarahmed.shoppinglist.core.data.DataStoreManager
import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.features.feature_list.data.remote.ShoppingListApi
import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_search.domain.repository.SearchRepo
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val api: ShoppingListApi,
    private val dataStoreManager: DataStoreManager
) : SearchRepo {
    override suspend fun getSearchResult(query: String?): Resource<List<ShoppingItem>> {
        return try {
            if (query?.trim().isNullOrEmpty()) {
                Resource.Error("Please, write something to search")
            } else {
                val token = dataStoreManager.getToken.first()
                val result = api.searchForItem(
                    token = "Bearer $token",
                    searchQuery = query!!
                )
                if (result.isNotEmpty()) {
                    Resource.Success(null,result)
                } else {
                    Resource.Error("No result..Try searching using another query")
                }
            }
        } catch (e: IOException) {
            Resource.Error("Couldn't reach the server. Check your Internet connection")
        } catch (e: HttpException) {
            Resource.Error("Something went wrong. Please try again later")
        }
    }
}