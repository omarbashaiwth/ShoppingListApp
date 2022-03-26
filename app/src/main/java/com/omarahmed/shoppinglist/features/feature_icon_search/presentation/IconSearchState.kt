package com.omarahmed.shoppinglist.features.feature_icon_search.presentation

import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response.Icon
import com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response.IconResponse

data class IconSearchState(
    val isLoading: Boolean = false,
    val iconSearchResult: IconResponse? = null
)
