package com.example.healthapp.ui.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.healthapp.domain.model.WaterIntake
import com.example.healthapp.data.repository.HealthRepository


class WaterViewModel(
    private val repository: HealthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WaterUiState>(WaterUiState.Loading)
    val uiState: StateFlow<WaterUiState> = _uiState

    init {
        loadWaterIntake()
    }

    fun loadWaterIntake() {
        viewModelScope.launch {
            try {
                _uiState.value = WaterUiState.Loading

                val list = repository.getTodayWaterIntake()

                _uiState.value =
                    if (list.isEmpty()) {
                        WaterUiState.Empty
                    } else {
                        WaterUiState.Success(
                            totalAmountMl = list.sumOf { it.amountMl },
                            items = list
                        )
                    }

            } catch (e: Exception) {
                _uiState.value = WaterUiState.Error(
                    e.message ?: "Unknown error"
                )
            }
        }
    }

    fun addWater(amountMl: Int) {
        viewModelScope.launch {
            repository.addWaterIntake(
                WaterIntake(
                    id = 0,
                    amountMl = amountMl,
                    timestamp = System.currentTimeMillis()
                )
            )
            loadWaterIntake()
        }
    }
}
