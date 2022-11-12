package com.muhammetaliilgaz.countries.data.model.countryDetail


import com.google.gson.annotations.SerializedName

data class DetailModel(
    @SerializedName("data")
    val detailData: DetailData
)