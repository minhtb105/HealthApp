package com.example.healthapp.ui.onboarding

import androidx.lifecycle.ViewModel
import com.example.healthapp.domain.model.Gender
import com.example.healthapp.domain.model.UserProfile
import com.example.healthapp.domain.repository.UserProfileRepository
import com.example.healthapp.domain.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val sessionManager: SessionManager
) : ViewModel() {

    fun saveProfile(heightCm: Int, birthDate: String, gender: Gender, onDone: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val userId = sessionManager.requireUserId()

                val profile = UserProfile(
                    userId = userId,
                    gender = gender,
                    birthDate = birthDate,
                    heightCm = heightCm,
                )

                userProfileRepository.upsertUserProfile(profile)
                onDone()
            } catch (e: Exception) {
                onError(e.message ?: "Save failed")
            }
        }
    }
}
