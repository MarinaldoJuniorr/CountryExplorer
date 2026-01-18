package com.example.countryexplorer.presentation.utils

import java.text.NumberFormat
import java.util.Locale

fun formatPopulation(pop: Long, locale: Locale = Locale.getDefault()): String {
    val nf = NumberFormat.getNumberInstance(locale).apply {
        maximumFractionDigits = 1
        minimumFractionDigits = 0
    }
    return when {
        pop >= 1_000_000_000 -> "${nf.format(pop / 1_000_000_000.0)} B"
        pop >= 1_000_000 -> "${nf.format(pop / 1_000_000.0)} M"
        pop >= 1_000 -> "${nf.format(pop / 1_000.0)} K"
        else -> pop.toString()
    }
}