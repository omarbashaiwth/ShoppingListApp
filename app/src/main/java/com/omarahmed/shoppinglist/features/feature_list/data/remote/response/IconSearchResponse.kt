package com.omarahmed.shoppinglist.features.feature_list.data.remote.response


import com.google.gson.annotations.SerializedName

data class IconSearchResponse(
    @SerializedName("icons")
    val icons: List<Icon>,
    @SerializedName("total_count")
    val totalCount: Int
)