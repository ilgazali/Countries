package com.muhammetaliilgaz.countries.data.model.countryDetail


import com.google.gson.annotations.SerializedName

data class DetailData(
    @SerializedName("callingCode")
    val callingCode: String,
    @SerializedName("capital")
    val capital: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("currencyCodes")
    val currencyCodes: List<String>,
    @SerializedName("flagImageUri")
    val flagImageUri: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("numRegions")
    val numRegions: Int,
    @SerializedName("wikiDataId")
    val wikiDataId: String
)