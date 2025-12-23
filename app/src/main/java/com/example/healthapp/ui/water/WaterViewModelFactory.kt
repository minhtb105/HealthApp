package com.example.healthapp.ui.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.healthapp.data.repository.HealthRepository
import com.example.healthapp.utils.TimeProvider


class WaterViewModelFactory(
    private val repository: HealthRepository,
    private val timeProvider: TimeProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WaterViewModel(repository, timeProvider) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
