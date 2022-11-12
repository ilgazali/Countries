package com.muhammetaliilgaz.countries.data.model.country


import com.google.gson.annotations.SerializedName

data class CountryModel(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("links")
    val links: List<Link>,
    @SerializedName("metadata")
    val metadata: Metadata
)