package com.example.healthapp.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.healthapp.data.remote.FirebaseAuthService
import com.example.healthapp.domain.repository.UserProfileRepository
import com.example.healthapp.domain.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authService: FirebaseAuthService,
    private val sessionManager: SessionManager,
    private val userProfileRepository: UserProfileRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<AuthUiState>(AuthUiState.Loading)
    val uiState: StateFlow<AuthUiState> = _uiState

    fun firebaseAuthWithGoogle(idToken: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState.Loading
            try {
                authService.signInWithGoogle(idToken)
                val userId = sessionManager.currentUserId
                if (userId == null) {
                    // unexpected, show error
                    _uiState.value = AuthUiState.Error("User id missing after sign-in")
                    return@launch
                }

                val profile = userProfileRepository.getUserProfile(userId)
                if (profile != null) {
                    // already signed up -> navigate to main
                    _uiState.value = AuthUiState.Success
                    // post an event / callback to Activity to navigate
                } else {
                    // no profile -> navigate to onboarding
                    _uiState.value = AuthUiState.Success // or a dedicated state
                    // Activity should open OnboardingActivity
                }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Login failed")
            }
        }
    }

    private fun onSuccess() {
        println("Log in successfully!")
    }

    private fun onError(exception: Exception) {
        println("Log in error: ${exception.message}")
    }
}
