package com.omarahmed.shoppinglist.features.feature_list.data.remote.response


import com.google.gson.annotations.SerializedName

data class Icon(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("containers")
    val containers: List<Container>,
    @SerializedName("icon_id")
    val iconId: Int,
    @SerializedName("is_icon_glyph")
    val isIconGlyph: Boolean,
    @SerializedName("is_premium")
    val isPremium: Boolean,
    @SerializedName("is_purchased")
    val isPurchased: Boolean,
    @SerializedName("prices")
    val prices: List<Price>,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("raster_sizes")
    val rasterSizes: List<RasterSize>,
    @SerializedName("styles")
    val styles: List<Style>,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("type")
    val type: String,
    @SerializedName("vector_sizes")
    val vectorSizes: List<VectorSize>
)