package com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response


import com.google.gson.annotations.SerializedName

data class RasterSize(
    @SerializedName("formats")
    val formats: List<Format>,
    @SerializedName("size")
    val size: Int,
    @SerializedName("size_height")
    val sizeHeight: Int,
    @SerializedName("size_width")
    val sizeWidth: Int
)