package com.example.countryexplorer.data.network

import com.example.countryexplorer.data.model.CountryDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Api {
    @GET("all?fields=name,capital,flags,region,languages,currencies,population,cca2,cca3")
    suspend fun getAllCountries(): List<CountryDto>

    @GET("alpha/{code}")
    suspend fun getCountryByCode(
        @Path("code") code: String,
        @Query("fields") fields: String = "name,capital,flags,region,languages,currencies,population,cca2"
    ): CountryDto
}