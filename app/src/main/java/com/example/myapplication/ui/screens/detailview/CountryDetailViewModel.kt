package com.example.myapplication.ui.screens.detailview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.CountryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CountryDetailViewModel @Inject constructor(private val countryUseCase: CountryUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<CountryDetailUiState>(CountryDetailUiState())
    val state: StateFlow<CountryDetailUiState> = _state.asStateFlow()

    fun loadCountryDetails(countryName: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val result = countryUseCase.loadCountryDetails(countryName)

            if (result.isSuccess) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    country = result.getOrNull()?.firstOrNull(),
                    error = null
                )
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    country = null,
                    error = result.exceptionOrNull()?.message ?: "Unknown error"
                )
            }
        }
    }


}

