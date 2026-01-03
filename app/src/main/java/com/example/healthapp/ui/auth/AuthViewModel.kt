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

                val userId = sessionManager.requireUserId()

                val profile = userProfileRepository.getUserProfile(userId)

                _uiState.value =
                    if (profile != null) {
                        AuthUiState.LoggedInExistingUser
                    } else {
                        AuthUiState.LoggedInNewUser
                    }
            } catch (e: Exception) {
                _uiState.value = AuthUiState.Error(e.message ?: "Login failed")
            }
        }
    }
}
