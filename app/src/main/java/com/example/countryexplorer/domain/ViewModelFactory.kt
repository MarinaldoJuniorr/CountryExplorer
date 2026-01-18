package com.example.countryexplorer.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.countryexplorer.domain.usecase.GetAllCountriesUseCase
import com.example.countryexplorer.domain.usecase.GetCountryByCodeUseCase
import com.example.countryexplorer.presentation.model.CountryDetailViewModel
import com.example.countryexplorer.presentation.model.CountryListViewModel

class ViewModelFactory(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val getCountryByCodeUseCase: GetCountryByCodeUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return when {
            modelClass.isAssignableFrom(CountryListViewModel::class.java) -> {
                CountryListViewModel(getAllCountriesUseCase) as T
            }

            modelClass.isAssignableFrom(CountryDetailViewModel::class.java) -> {
                CountryDetailViewModel(getCountryByCodeUseCase) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return create(modelClass, CreationExtras.Empty)
    }
}