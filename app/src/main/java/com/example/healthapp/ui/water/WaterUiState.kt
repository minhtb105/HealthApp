package com.example.healthapp.ui.water

import com.example.healthapp.domain.model.WaterIntake


sealed class WaterUiState {

    object Loading : WaterUiState()

    data class Success(
        val totalAmountMl: Int,
        val items: List<WaterIntake>
    ) : WaterUiState()

    object Empty : WaterUiState()

    data class Error(
        val message: String
    ) : WaterUiState()
}
