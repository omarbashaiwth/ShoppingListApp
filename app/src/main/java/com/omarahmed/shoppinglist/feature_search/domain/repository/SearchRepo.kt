package com.omarahmed.shoppinglist.feature_search.domain.repository

import com.omarahmed.shoppinglist.core.data.model.ShoppingItem
import com.omarahmed.shoppinglist.core.util.Resource

interface SearchRepo {

    suspend fun getSearchResult(query: String?): Resource<List<ShoppingItem>>
}