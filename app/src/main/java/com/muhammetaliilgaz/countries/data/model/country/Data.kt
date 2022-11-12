package com.muhammetaliilgaz.countries.data.model.country


import com.google.gson.annotations.SerializedName

data class Data(
    val id : Int = 0,
    @SerializedName("code")
    val code: String,

    @SerializedName("name")
    val name: String,
    @SerializedName("wikiDataId")
    val wikiDataId: String,

    var favorite : Boolean
)