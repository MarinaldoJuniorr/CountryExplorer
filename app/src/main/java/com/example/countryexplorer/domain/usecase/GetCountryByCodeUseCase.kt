package com.example.countryexplorer.domain.usecase

import com.example.countryexplorer.data.repository.CountryRepository
import com.example.countryexplorer.domain.model.CountryDomain
import com.example.countryexplorer.domain.model.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetCountryByCodeUseCase(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(code: String): Result<CountryDomain> = withContext(Dispatchers.IO){
        try {
            val dto = repository.getCountryByCode(code)
            if(dto != null){
                Result.success(dto.toDomain())
            } else{
                Result.failure(Exception("Country not found"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}