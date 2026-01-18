package com.example.countryexplorer.data.repository

import com.example.countryexplorer.data.model.CountryDto
import com.example.countryexplorer.data.network.Api

class CountryRepository(private val api: Api) {

    suspend fun getAllCountries(): List<CountryDto> =
        api.getAllCountries()

    suspend fun getCountryByCode(code: String): CountryDto? =
        try {
            api.getCountryByCode(code)
        } catch (e: Exception){
            null
        }
}