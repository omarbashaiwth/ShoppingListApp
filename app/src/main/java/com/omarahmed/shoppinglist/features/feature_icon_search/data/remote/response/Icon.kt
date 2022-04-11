package com.omarahmed.shoppinglist.features.feature_icon_search.data.remote.response


import com.google.gson.annotations.SerializedName

data class Icon(
    @SerializedName("icon_id")
    val iconId: Int,
    @SerializedName("raster_sizes")
    val rasterSizes: List<RasterSize>,
)