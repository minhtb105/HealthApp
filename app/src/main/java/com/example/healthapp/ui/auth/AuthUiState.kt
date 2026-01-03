package com.example.healthapp.ui.auth


sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object LoggedInExistingUser : AuthUiState()
    object LoggedInNewUser : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
