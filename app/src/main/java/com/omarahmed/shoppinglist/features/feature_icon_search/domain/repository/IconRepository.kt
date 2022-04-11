package com.omarahmed.shoppinglist.features.feature_icon_search.domain.repository

import com.omarahmed.shoppinglist.core.util.Resource
import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response.Icon
import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response.IconResponse

interface IconRepository {

    suspend fun getIcons(
        query: String
    ): Resource<IconResponse>
}