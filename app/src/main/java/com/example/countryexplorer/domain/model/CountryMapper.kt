package com.example.countryexplorer.domain.model

import com.example.countryexplorer.data.model.CountryDto

fun CountryDto.toDomain(): CountryDomain{
    val language = languages?.values?.joinToString(", ") ?: "Unknown"
    val currency = currencies?.values
        ?.joinToString(", ") { "${it.name ?: "Unknown"} (${it.symbol ?: ""})" }
        ?: "Unknown"

    return CountryDomain(
        name = name.common,
        capital = capital?.firstOrNull() ?: "No capital",
        region = region ?: "No region",
        flagUrl = flags.png,
        population = population ?: 0L,
        language = language,
        currency = currency,
        code = cca2 ?: cca3 ?: ""
    )
}
