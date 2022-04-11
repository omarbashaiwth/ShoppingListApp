package com.omarahmed.shoppinglist.features.feature_list.data.remote.response


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("identifier")
    val identifier: String,
    @SerializedName("name")
    val name: String
)