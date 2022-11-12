package com.muhammetaliilgaz.countries.service.api

import com.muhammetaliilgaz.countries.data.model.country.CountryModel
import com.muhammetaliilgaz.countries.data.model.countryDetail.DetailModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("countries")
    fun getCountries(
        @Header("X-RapidAPI-Key") token: String,
        @Query("limit") limit: String
    ):Call<CountryModel>

    @GET("countries/{countryCode}")
    fun getCountryDetails(
        @Path("countryCode") countryCode: String,
        @Header("X-RapidAPI-Key") token: String
    ):Call<DetailModel>



}