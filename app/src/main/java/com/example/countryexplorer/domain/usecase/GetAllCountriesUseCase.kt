package com.example.countryexplorer.domain.usecase

import com.example.countryexplorer.data.repository.CountryRepository
import com.example.countryexplorer.domain.model.CountryDomain
import com.example.countryexplorer.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GetAllCountriesUseCase(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Result<List<CountryDomain>> = withContext(Dispatchers.IO) {
        try {
            val result = repository.getAllCountries().map { it.toDomain() }
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}