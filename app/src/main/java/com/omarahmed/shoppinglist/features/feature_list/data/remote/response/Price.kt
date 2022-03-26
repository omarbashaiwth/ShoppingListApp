package com.omarahmed.shoppinglist.features.feature_list.data.remote.response


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("license")
    val license: License,
    @SerializedName("price")
    val price: Double
)