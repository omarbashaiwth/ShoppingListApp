package com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response


import com.google.gson.annotations.SerializedName

data class IconResponse(
    @SerializedName("icons")
    val icons: List<Icon>,
    @SerializedName("total_count")
    val totalCount: Int
)