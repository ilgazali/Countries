package com.muhammetaliilgaz.countries.data.model.country


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("currentOffset")
    val currentOffset: Int,
    @SerializedName("totalCount")
    val totalCount: Int
)