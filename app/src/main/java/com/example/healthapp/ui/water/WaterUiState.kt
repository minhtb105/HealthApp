package com.example.healthapp.ui.water

import com.example.healthapp.domain.model.WaterIntake


/**
 * Represents all possible UI states for the Water Intake screen.
 *
 * This sealed class is used by the ViewModel to expose a single source of truth
 * for the UI. Each state reflects a real situation that the user can experience
 * while interacting with the water tracking feature.
 */
sealed class WaterUiState {

    /**
     * Indicates that the app is currently loading water intake data.
     *
     * This state is typically shown:
     * - When the screen is first opened
     * - After adding a new water intake entry
     * - While fetching data from the database or remote source
     */
    object Loading : WaterUiState()

    /**
     * Indicates that water intake data has been successfully loaded.
     *
     * @property totalAmountMl The total amount of water consumed (in milliliters).
     * @property items A list of individual water intake records.
     *
     * This state is shown when at least one water intake entry exists.
     */
    data class Success(
        val totalAmountMl: Int,
        val items: List<WaterIntake>
    ) : WaterUiState()

    /**
     * Indicates that no water intake data is available.
     *
     * This state is usually shown when:
     * - The user has just installed the app
     * - The user has not logged any water intake yet
     *
     * It is a normal and expected state, not an error.
     */
    object Empty : WaterUiState()

    /**
     * Indicates that an unexpected error occurred while loading or processing data.
     *
     * @property message A human-readable error message that can be displayed to the user.
     *
     * This state is used for situations such as database errors or unexpected exceptions.
     */
    data class Error(
        val message: String
    ) : WaterUiState()
}
