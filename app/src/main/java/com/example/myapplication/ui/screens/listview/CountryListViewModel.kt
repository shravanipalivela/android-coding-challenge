package com.example.myapplication.ui.screens.listview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.CountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val countryUseCase: CountryUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    ViewModel() {

    private val _countriesState = MutableStateFlow<CountryListUiState>(CountryListUiState())
    val countriesState: StateFlow<CountryListUiState> = _countriesState.asStateFlow()

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch(dispatcher) {
            _countriesState.value = _countriesState.value.copy(isLoading = true)
            val countries = countryUseCase.loadCountries()
            if (countries.isSuccess) {
                _countriesState.value = _countriesState.value.copy(
                    isLoading = false,
                    countries = countries.getOrDefault(emptyList()),
                    error = null
                )
            } else {
                val errorMessage =
                    countries.exceptionOrNull()?.message ?: "Unknown error has occurred"
                _countriesState.value = _countriesState.value.copy(
                    isLoading = false,
                    countries = emptyList(),
                    error = errorMessage
                )
            }
        }
    }
}