package com.omarahmed.shoppinglist.features.feature_list.data.remote.response


import com.google.gson.annotations.SerializedName

data class FormatX(
    @SerializedName("download_url")
    val downloadUrl: String,
    @SerializedName("format")
    val format: String
)