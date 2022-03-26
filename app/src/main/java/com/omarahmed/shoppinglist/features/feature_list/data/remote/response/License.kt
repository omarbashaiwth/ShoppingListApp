package com.omarahmed.shoppinglist.features.feature_list.data.remote.response


import com.google.gson.annotations.SerializedName

data class License(
    @SerializedName("license_id")
    val licenseId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("url")
    val url: String
)