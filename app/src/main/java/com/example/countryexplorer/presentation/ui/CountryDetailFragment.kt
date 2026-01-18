package com.example.countryexplorer.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.countryexplorer.data.network.RetrofitClient
import com.example.countryexplorer.data.repository.CountryRepository
import com.example.countryexplorer.databinding.FragmentCountryDetailBinding
import com.example.countryexplorer.domain.ViewModelFactory
import com.example.countryexplorer.domain.usecase.GetAllCountriesUseCase
import com.example.countryexplorer.domain.usecase.GetCountryByCodeUseCase
import com.example.countryexplorer.presentation.model.CountryDetailViewModel
import com.example.countryexplorer.presentation.utils.formatPopulation

class CountryDetailFragment : Fragment() {

    private var _binding: FragmentCountryDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CountryDetailViewModel

    private val args: CountryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupViewModel()
        observe()

        val code = args.code
        if (code.isBlank()) {
            Toast.makeText(requireContext(), "Country code is empty", Toast.LENGTH_LONG).show()
            return
        }
        viewModel.loadCountry(code)
    }

    private fun setupViewModel() {
        val api = RetrofitClient.api
        val repository = CountryRepository(api)

        val getAllCountriesUseCase = GetAllCountriesUseCase(repository)
        val getCountryByCodeUseCase = GetCountryByCodeUseCase(repository)

        val factory = ViewModelFactory(
            getAllCountriesUseCase = getAllCountriesUseCase,
            getCountryByCodeUseCase = getCountryByCodeUseCase
        )

        viewModel = ViewModelProvider(this, factory)[CountryDetailViewModel::class.java]
    }

    private fun observe() {
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
            binding.allDataGroup.isVisible = !loading
        }

        viewModel.country.observe(viewLifecycleOwner) { country ->
            binding.tvCountyName.text = country.name
            binding.tvCapitalResult.text = country.capital
            binding.tvRegionResult.text = country.region
            binding.tvPopulationResult.text = formatPopulation(country.population) // K/M/B com locale
            binding.tvLanguageResult.text = country.language
            binding.tvCurrencyResult.text = country.currency

            Glide.with(this)
                .load(country.flagUrl)
                .into(binding.flagDetailImg)
        }

        viewModel.error.observe(viewLifecycleOwner) { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}